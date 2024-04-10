package com.adt.hrms.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.request.EmployeeRequest;
import com.adt.hrms.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/employee")
public class EmployeeOperationController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmployeeService employeeService;

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getById/{empId}")
	public ResponseEntity<Employee> getEmp(@PathVariable("empId") int empId) {
		LOGGER.info("Employeeservice:employee:getEmp info level log message");
		return new ResponseEntity<>(employeeService.getEmp(empId), HttpStatus.OK);
	}

	@PostMapping("/addEmp")
	public ResponseEntity<String> saveEmp(@RequestBody Employee emp) {
		LOGGER.info("Employeeservice:employee:saveEmp info level log message");
		return new ResponseEntity<>(employeeService.saveEmp(emp), HttpStatus.OK);
	}

	// JIRA NO. :- HRMS-106(Bug Resolved) START---
	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getAllEmp")
	public ResponseEntity<Page<Employee>> getAllEmps(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
													 @RequestParam(value = "size", defaultValue = "100", required = false) int size) {
		LOGGER.info("Employee-service:employee:getAllEmp info level log message");
		return new ResponseEntity<>(employeeService.getAllEmps(page, size), HttpStatus.OK);
	}
	// JIRA NO. :- HRMS-106(Bug Resolved) END---

//	@PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER',T(java.util.Map).of('currentUser', #empId))")
	@PutMapping("/updateEmp")
	public ResponseEntity<String> updateEmp(@RequestPart(value = "resume", required = false) MultipartFile resume,
			@RequestPart String emp, @RequestPart(value = "aadhar", required = false) MultipartFile aadhar,
			@RequestPart(value = "pan", required = false) MultipartFile pan) throws IOException {
		try {
			LOGGER.info("Employeeservice:employee:updateEmp info level log message");
			ObjectMapper mapper = new ObjectMapper();
			EmployeeRequest empRequest = mapper.readValue(emp, EmployeeRequest.class);
			return new ResponseEntity<>(employeeService.updateEmp(empRequest, resume, aadhar, pan), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// HRMS-82-Start
	@GetMapping("/downloadResume/{employeeId}")
	public ResponseEntity<byte[]> downloadResume(@PathVariable int employeeId) {
		try {
			Employee employee = employeeService.getEmployeeById(employeeId);
			if (employee == null) {
				return ResponseEntity.notFound().build();
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDisposition(
					ContentDisposition.parse("attachment; filename=\"" + employee.getFirstName() + "_Resume.pdf\""));

			return new ResponseEntity<>(employee.getResume(), headers, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	// HRMS-82-End

	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager START---
	@PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER')")
	@GetMapping("downloadAadharCard/{id}")
	public ResponseEntity<byte[]> downloadAadhar(@PathVariable int id, HttpServletResponse resp) throws IOException {
		LOGGER.info("EmployeeService:EmployeeOperationController:downloadAadhar:AadharCard info level log message");

		return ResponseEntity.ok(employeeService.downloadAadharCard(id, resp));
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN') or @auth.allow('ROLE_USER')")
	@GetMapping("downloadPanCard/{id}")
	public ResponseEntity<byte[]> downloadPan(@PathVariable int id, HttpServletResponse resp) throws IOException {
		LOGGER.info("EmployeeService:EmployeeOperationController:downloadPan:PanCard info level log message");

		return ResponseEntity.ok(employeeService.downloadPanCard(id, resp));
	}
	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager END---

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<String> deleteEmp(@PathVariable("empId") int empId) {
		LOGGER.info("Employeeservice:employee:deleteEmp info level log message");
		return new ResponseEntity<String>(employeeService.deleteEmpById(empId), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/findStatusById/{empId}")
	public ResponseEntity<EmployeeStatus> findEmployeeByEmployeeId(@PathVariable("empId") Integer empId) {
		LOGGER.info("Employeeservice:employee:findEmployeeByEmployeeId info level log message");
		return new ResponseEntity<EmployeeStatus>(employeeService.getEmployeeById(empId), HttpStatus.OK);

	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/searchByName")
	public ResponseEntity<List<Employee>> SearchByName(@RequestParam("query") String name) {
		LOGGER.info("Employeeservice:employee:SearchByName info level log message");
		return ResponseEntity.ok(employeeService.SearchByName(name));
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/searchByEmail")
	public ResponseEntity<List<Employee>> SearchByEmail(@RequestParam("query") String email) {
		LOGGER.info("Employeeservice:employee:SearchByEmail info level log message");
		return ResponseEntity.ok(employeeService.SearchByEmail(email));
	}

}
