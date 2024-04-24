package com.adt.hrms.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PreAuthorize("@auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
    @GetMapping("/getById/{empId}")
    public ResponseEntity<Employee> getPersonalDetailsById(@PathVariable("empId") int empId) {
        LOGGER.info("Employeeservice:employee:getEmp info level log message");
        return new ResponseEntity<>(employeeService.getEmp(empId), HttpStatus.OK);
    }


    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/getAllEmp")
    public ResponseEntity<Page<Employee>> getAllEmps(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("Employee-service:employee:getAllEmp info level log message");
        return new ResponseEntity<>(employeeService.getAllEmps(page, size), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/searchByName")
    public ResponseEntity<Page<Employee>> SearchByName(@RequestParam("query") String name, @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                       @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("Employeeservice:employee:SearchByName info level log message");
        return ResponseEntity.ok(employeeService.SearchByName(name, page, size));
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/searchByEmail")
    public ResponseEntity<Page<Employee>> SearchByEmail(@RequestParam("query") String email, @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("Employeeservice:employee:SearchByEmail info level log message");
        return ResponseEntity.ok(employeeService.SearchByEmail(email, page, size));
    }


    @PreAuthorize("@auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #emp.getEmployeeId()))")
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

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PutMapping("/updateEmpById")
    public ResponseEntity<String> updateEmpById(@RequestBody EmployeeUpdateByAdminDTO emp) {
        try {
            LOGGER.info("Employeeservice:employee:updateEmp info level log message");
            return new ResponseEntity<>(employeeService.updateEmpById(emp), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #employeeId))")
    @GetMapping("/downloadDocument/{employeeId}/{documentTypeId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable int employeeId, @PathVariable int documentTypeId, HttpServletResponse resp) {
        try {
            return ResponseEntity.ok(employeeDocumentService.getEmployeeDocumentById(employeeId, documentTypeId, resp));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("@auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
    @PostMapping("/uploadDocument/{empId}/{docTypeId}")
    public ResponseEntity<String> uploadDocument(@PathVariable int empId, @PathVariable int docTypeId, @RequestPart MultipartFile document) throws IOException {
        try {
            LOGGER.info("EmployeeDocumentService:employee:addDocument info level log message");
            EmployeeDocumentDTO docRequest = new EmployeeDocumentDTO();
            docRequest.setEmpId(empId);
            docRequest.setDocTypeId(docTypeId);
            return new ResponseEntity<>(employeeDocumentService.saveDocument(docRequest, document), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("@auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
    @DeleteMapping("/deleteDocument/{empId}/{docTypeId}")
    public ResponseEntity<String> deleteDocument(@PathVariable int empId, @PathVariable int docTypeId) throws IOException {
        try {
            LOGGER.info("EmployeeDocumentService:employee:addDocument info level log message");
            return new ResponseEntity<>(employeeDocumentService.deleteDocument(empId,docTypeId), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PostMapping("/addDocumentType")
    public ResponseEntity<String> addDocumentType(@RequestBody DocumentType documentType) throws IOException {
        LOGGER.info("EmployeeDocumentService:employee:addDocumentType info level log message");
        return new ResponseEntity<>(documentTypeService.saveDocumentType(documentType), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @DeleteMapping("/deleteDocumentType/{docTypeId}")
    public ResponseEntity<String> deleteDocumentType(@PathVariable("docTypeId") int docTypeId) {
        LOGGER.info("DocumentTypeService:DocumentType:deleteDocumentType info level log message");
        return new ResponseEntity<String>(documentTypeService.deleteDocTypeById(docTypeId), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
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

    @PreAuthorize("@auth.allow('ROLE_USER')")
    @GetMapping("/getDocumentTypes")
    public ResponseEntity<List<DocumentType>> getDocumentTypes() {
        LOGGER.info("EmployeeDocument:employee:getDocumentTypes info level log message");
        return new ResponseEntity<List<DocumentType>>(documentTypeService.getDocumentTypes(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/getAllDocumentDetails")
    public ResponseEntity<Page<EmployeeDocument>> getAllDocumentDetails(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        LOGGER.info("EmployeeDocument:employee:getAllDocumentDetails info level log message");
        return new ResponseEntity<>(employeeDocumentService.getAllDocumentDetails(page, size), HttpStatus.OK);
    }
//    @PreAuthorize("@auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
    @GetMapping("/getAllDocumentDetailsByEmpId/{empId}")
    public ResponseEntity<List<EmployeeDocument>> getAllDocumentDetailsByEmpId(@PathVariable int empId) {
        LOGGER.info("EmployeeDocument:employee:getAllDocumentDetails info level log message");
        return new ResponseEntity<>(employeeDocumentService.getAllDocumentDetailsByEmpId(empId), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #emp.getEmpId()))")
    @PostMapping("/updatePayrollByUser")
    public ResponseEntity<String> updateEmpPayrollDetailsByUser(@RequestBody EmpPayrollDetails emp) {
        LOGGER.info("Employee-service:employee:updateEmpPayrollDetails info level log message");
        return ResponseEntity.ok(empPayrollDetailsService.updateEmpPayrollDetailsByUser(emp));
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PostMapping("/updatePayrollByAdmin")
    public ResponseEntity<String> updateEmpPayrollByAdmin(@RequestBody EmpPayrollDetails emp) {
        LOGGER.info("Employee-service:employee:updateEmpPayroll info level log message");
        return ResponseEntity.ok(empPayrollDetailsService.updateEmpPayroll(emp));
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
    @GetMapping("/getEmpPayrollById/{empId}")
    public ResponseEntity<EmpPayrollDetails> getEmpPayrollDetails(@PathVariable("empId") Integer empId) {
        LOGGER.info("Employee-service:employee:getEmpPayrollDetails info level log message");
        return ResponseEntity.ok(empPayrollDetailsService.getEmpPayrollDetails(empId));
    }

    //	@PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER')")
//	@GetMapping("downloadAadharCard/{id}")
//	public ResponseEntity<byte[]> downloadAadhar(@PathVariable int id, HttpServletResponse resp) throws IOException {
//		LOGGER.info("EmployeeService:EmployeeOperationController:downloadAadhar:AadharCard info level log message");
//
//		return ResponseEntity.ok(employeeService.downloadAadharCard(id, resp));
//	}

//	@PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER')")
//	@GetMapping("downloadPanCard/{id}")
//	public ResponseEntity<byte[]> downloadPan(@PathVariable int id, HttpServletResponse resp) throws IOException {
//		LOGGER.info("EmployeeService:EmployeeOperationController:downloadPan:PanCard info level log message");
//
//		return ResponseEntity.ok(employeeService.downloadPanCard(id, resp));
//	}
    // JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager END---

//	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
//	@DeleteMapping("/delete/{empId}")
//	public ResponseEntity<String> deleteEmp(@PathVariable("empId") int empId) {
//		LOGGER.info("Employeeservice:employee:deleteEmp info level log message");
//		return new ResponseEntity<String>(employeeService.deleteEmpById(empId), HttpStatus.OK);
//	}

//	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
//	@GetMapping("/findStatusById/{empId}")
//	public ResponseEntity<EmployeeStatus> findEmployeeStatusByEmployeeId(@PathVariable("empId") Integer empId) {
//		LOGGER.info("Employeeservice:employee:findEmployeeByEmployeeId info level log message");
//		return new ResponseEntity<EmployeeStatus>(employeeService.getEmployeeById(empId), HttpStatus.OK);
//	}


}
