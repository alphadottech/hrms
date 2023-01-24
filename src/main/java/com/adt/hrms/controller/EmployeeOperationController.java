package com.adt.hrms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeOperationController {
	
private static final Logger LOGGER = LogManager.getLogger(EmployeeOperationController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
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

	@GetMapping("/getAllEmp")
	public ResponseEntity<List<Employee>> getAllEmps() {
		LOGGER.info("Employeeservice:employee:getAllEmps info level log message");
			return new ResponseEntity<>(employeeService.getAllEmps(), HttpStatus.OK);
	}
	
	@PutMapping("/updateEmp")
	public ResponseEntity<String> updateEmp(@RequestBody Employee emp) {
		LOGGER.info("Employeeservice:employee:updateEmp info level log message");
		return new ResponseEntity<>(employeeService.updateEmp(emp), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<String> deleteEmp(@PathVariable("empId") int empId) {
		LOGGER.info("Employeeservice:employee:deleteEmp info level log message");
		return new ResponseEntity<String>(employeeService.deleteEmpById(empId), HttpStatus.OK);
	}
	@GetMapping("/findStatusById/{empId}")
	public ResponseEntity<EmployeeStatus> findEmployeeByEmployeeId(@PathVariable("empId") Integer empId ){
		LOGGER.info("Employeeservice:employee:findEmployeeByEmployeeId info level log message");
		return new ResponseEntity<EmployeeStatus>(employeeService.getEmployeeById(empId), HttpStatus.OK);
		
	}
	
	@GetMapping("/searchByFirstLastname")
	public ResponseEntity<List<Employee>> SearchEmployee(@RequestParam("query") String query){
		LOGGER.info("Employeeservice:employee:SearchEmployeeByFirstLastName info level log message");
		return ResponseEntity.ok(employeeService.SearchEmployee(query));
	}
	
	@GetMapping("/emailId")
	public ResponseEntity<List<Employee>> SearchByEmailId(@RequestParam("query") String query){
		LOGGER.info("Employeeservice:employee:SearchByEmailId info level log message");
		return ResponseEntity.ok(employeeService.SearchByEmailId(query));
	}

}
