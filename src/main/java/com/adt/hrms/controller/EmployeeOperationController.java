package com.adt.hrms.controller;

import com.adt.hrms.model.DocumentType;
import com.adt.hrms.model.EmpPayrollDetails;
import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeDocument;
import com.adt.hrms.request.EmployeeDocumentDTO;
import com.adt.hrms.request.EmployeeRequest;
import com.adt.hrms.request.EmployeeUpdateByAdminDTO;
import com.adt.hrms.service.DocumentTypeService;
import com.adt.hrms.service.EmpPayrollDetailsService;
import com.adt.hrms.service.EmployeeDocumentService;
import com.adt.hrms.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employee")
public class EmployeeOperationController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDocumentService employeeDocumentService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private EmpPayrollDetailsService empPayrollDetailsService;


    @PreAuthorize("@auth.allow('GET_EMPLOYEE_PERSONAL_DETAILS_BY_ID')")
    @GetMapping("/getById/{empId}")
    public ResponseEntity<Employee> getPersonalDetailsById(@PathVariable("empId") int empId) {
        LOGGER.info("Employeeservice:employee:getEmp info level log message");
        return new ResponseEntity<>(employeeService.getEmp(empId), HttpStatus.OK);
    }


    @PreAuthorize("@auth.allow('GET_ALL_EMPLOYEE_PERSONAL_DETAILS')")
    @GetMapping("/getAllEmp")
    public ResponseEntity<Page<Employee>> getAllEmps(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        return new ResponseEntity<>(employeeService.getAllEmps(page, size), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('SEARCH_EMPLOYEE_BY_NAME')")
    @GetMapping("/searchByName")
    public ResponseEntity<Page<Employee>> SearchByName(@RequestParam("query") String name, @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                       @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("Employeeservice:employee:SearchByName info level log message");
        return ResponseEntity.ok(employeeService.SearchByName(name, page, size));
    }

    @PreAuthorize("@auth.allow('SEARCH_EMPLOYEE_BY_EMAIL')")
    @GetMapping("/searchByEmail")
    public ResponseEntity<Page<Employee>> SearchByEmail(@RequestParam("query") String email, @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("Employeeservice:employee:SearchByEmail info level log message");
        return ResponseEntity.ok(employeeService.SearchByEmail(email, page, size));
    }


    @PreAuthorize("@auth.allow('UPDATE_EMPLOYEE_PERSONAL_DETAILS_BY_ID',T(java.util.Map).of('currentUser', #emp.getEmployeeId()))")
    @PutMapping("/updatePersonalDetailsById")
    public ResponseEntity<String> updatePersonalDetailsById(@RequestBody EmployeeRequest emp) {
        try {
            LOGGER.info("Employeeservice:employee:updateEmp info level log message");
            return new ResponseEntity<>(employeeService.updateEmp(emp), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('UPDATE_EMPLOYEE_DETAILS_BY_ADMIN')")
    @CrossOrigin(origins = "*")
    @PutMapping("/updateEmpById")
    public ResponseEntity<String> updateEmpById(@RequestBody Employee emp) {
        try {
            LOGGER.info("Employee service:employee:updateEmp info level log message");
            return new ResponseEntity<>(employeeService.updateEmpById(emp), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('DOWNLOAD_DOCUMENT_BY_EMPLOYEE_ID_AND_DOC_TYPE_ID')")
    @GetMapping("/downloadDocument/{employeeId}/{documentTypeId}")
    public ResponseEntity<String> downloadDocument(@PathVariable int employeeId, @PathVariable int documentTypeId, HttpServletResponse resp) {
        try {
            return ResponseEntity.ok(employeeDocumentService.getEmployeeDocumentById(employeeId, documentTypeId, resp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('UPLOAD_EMPLOYEE_DOCUMENT_BY_DOCUMENT_TYPE_ID')")
    @PostMapping("/uploadDocument/{empId}/{docTypeId}")
    public ResponseEntity<String> uploadDocument(@PathVariable int empId, @PathVariable int docTypeId, @RequestPart MultipartFile document) throws IOException {
        try {
            LOGGER.info("EmployeeDocumentService:employee:addDocument info level log message");
            EmployeeDocumentDTO docRequest = new EmployeeDocumentDTO();
            docRequest.setEmpId(empId);
            docRequest.setDocTypeId(docTypeId);
            return new ResponseEntity<>(employeeDocumentService.saveDocument(docRequest, document), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error uploading document: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading document: " + e.getMessage());
        }
    }


    @PreAuthorize("@auth.allow('DELETE_DOCUMENT_BY_EMPLOYEE_ID_AND_DOCUMENT_TYPE_ID')")
    @DeleteMapping("/deleteDocument/{empId}/{docTypeId}")
    public ResponseEntity<String> deleteDocument(@PathVariable int empId, @PathVariable int docTypeId) throws IOException {
        try {
            LOGGER.info("EmployeeDocumentService:employee:addDocument info level log message");
            return new ResponseEntity<>(employeeDocumentService.deleteDocument(empId, docTypeId), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("@auth.allow('SAVE_DOCUMENT_TYPE_TO_DB')")
    @PostMapping("/addDocumentType")
    public ResponseEntity<String> addDocumentType(@RequestBody DocumentType documentType) throws IOException {
        LOGGER.info("EmployeeDocumentService:employee:addDocumentType info level log message");
        return new ResponseEntity<>(documentTypeService.saveDocumentType(documentType), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('DELETE_DOCUMENT_TYPE_FROM_DB')")
    @DeleteMapping("/deleteDocumentType/{docTypeId}")
    public ResponseEntity<String> deleteDocumentType(@PathVariable("docTypeId") int docTypeId) {
        LOGGER.info("DocumentTypeService:DocumentType:deleteDocumentType info level log message");
        return new ResponseEntity<String>(documentTypeService.deleteDocTypeById(docTypeId), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('UPDATE_DOCUMENT_TYPE')")
    @PutMapping("/updateDocumentTypeById")
    public ResponseEntity<String> updateDocumentTypeById(@RequestBody DocumentType documentType) {
        try {
            LOGGER.info("DocumentTypeService:Document:updateDocumentType info level log message");
            return new ResponseEntity<>(documentTypeService.updateDocumentType(documentType), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('GET_ALL_DOCUMENT_TYPES')")
    @GetMapping("/getDocumentTypes")
    public ResponseEntity<List<DocumentType>> getDocumentTypes() {
        LOGGER.info("EmployeeDocument:employee:getDocumentTypes info level log message");
        return new ResponseEntity<List<DocumentType>>(documentTypeService.getDocumentTypes(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_ALL_EMPLOYEE_DOCUMENT_DETAILS')")
    @GetMapping("/getAllDocumentDetails")
    public ResponseEntity<Page<EmployeeDocument>> getAllDocumentDetails(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("EmployeeDocument:employee:getAllDocumentDetails info level log message");
        return new ResponseEntity<>(employeeDocumentService.getAllDocumentDetails(page, size), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_ALL_DOCUMENT_DETAILS_BY_EMP_ID')")
    @GetMapping("/getAllDocumentDetailsByEmpId/{empId}")
    public ResponseEntity<List<EmployeeDocument>> getAllDocumentDetailsByEmpId(@PathVariable int empId) {
        LOGGER.info("EmployeeDocument:employee:getAllDocumentDetails info level log message");
        return new ResponseEntity<>(employeeDocumentService.getAllDocumentDetailsByEmpId(empId), HttpStatus.OK);
    }


    @PreAuthorize("@auth.allow('UPDATE_PAYROLL_DETAILS_BY_USER',T(java.util.Map).of('currentUser', #emp.getEmpId()))")
    @PostMapping("/updatePayrollByUser")
    public ResponseEntity<String> updateEmpPayrollDetailsByUser(@RequestBody EmpPayrollDetails emp) {
        LOGGER.info("Employee-service:employee:updateEmpPayrollDetails info level log message");
        return ResponseEntity.ok(empPayrollDetailsService.updateEmpPayrollDetailsByUser(emp));
    }

    @PreAuthorize("@auth.allow('UPDATE_PAYROLL_DETAILS_BY_ADMIN')")
    @PostMapping("/updatePayrollByAdmin")
    public ResponseEntity<String> updateEmpPayrollByAdmin(@RequestBody EmpPayrollDetails emp) {
        LOGGER.info("Employee-service:employee:updateEmpPayroll info level log message");
        return ResponseEntity.ok(empPayrollDetailsService.updateEmpPayroll(emp));
    }

    @PreAuthorize("@auth.allow('GET_EMPLOYEE_PAYROLL_DETAILS_BY_EMPLOYEE_ID') or @auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
    @GetMapping("/getEmpPayrollById/{empId}")
    public ResponseEntity<?> getEmpPayrollDetails(@PathVariable("empId") Integer empId) {
        LOGGER.info("Employee-service:employee:getEmpPayrollDetails info level log message");
		EmpPayrollDetails payrolldetails = empPayrollDetailsService.getEmpPayrollDetails(empId);
		if (payrolldetails != null) {
			return ResponseEntity.ok(payrolldetails);
		}
		return ResponseEntity.ok("Payroll details not found");
    }

    @PreAuthorize("@auth.allow('SEARCH_EMPLOYEE_BY_CRITERIA')")
    @GetMapping("/searchEmployees")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "mobileNo", required = false) Long mobileNo,
            @RequestParam(value = "firstLetter", required = false) String firstLetter,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        LOGGER.info("EmployeeService: Searching for employees");
        Page<Employee> searchResult = employeeService.searchEmployees(firstName, lastName, email, mobileNo, firstLetter, page, size);
        return ResponseEntity.ok(searchResult);
    }

    @PreAuthorize("@auth.allow('GET_PROFILE_PICTURE_BY_EMPLOYEE_ID',T(java.util.Map).of('currentUser', #employeeId))")
    @GetMapping("/profilePicture/{employeeId}")
    public ResponseEntity<String> getUserProfilePicture(@PathVariable("employeeId") int employeeId, HttpServletResponse resp) {
        int docTypeId = employeeDocumentService.getDocumentTypeId("User Profile Picture");
        try {
            return ResponseEntity.ok(employeeDocumentService.getEmployeeDocumentById(employeeId, docTypeId, resp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('DELETE_EMPLOYEE_BY_EMP_ID')")
    @DeleteMapping("/deleteEmployeeById/{employeeId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable ("employeeId") int employeeId ){
        LOGGER.info("Deleted employee with id: {}", employeeId);
        return new ResponseEntity<String>(employeeService.deleteEmpById(employeeId), HttpStatus.OK);
    }


}
