package com.adt.hrms.controller;

import java.util.List;

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

import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeOperationController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/getById/{empId}")
	public ResponseEntity<Employee> getEmp(@PathVariable("empId") int empId) {
		return new ResponseEntity<>(employeeService.getEmp(empId), HttpStatus.OK);
	}

	@PostMapping("/addEmp")
	public ResponseEntity<String> saveEmp(@RequestBody Employee emp) {
		return new ResponseEntity<>(employeeService.saveEmp(emp), HttpStatus.OK);
	}

	@GetMapping("/getAllEmp")
	public ResponseEntity<List<Employee>> getAllEmps() {
			return new ResponseEntity<>(employeeService.getAllEmps(), HttpStatus.OK);
	}
	
	@PutMapping("/updateEmp")
	public ResponseEntity<Employee> updateEmp(@RequestBody Employee emp) {
		return new ResponseEntity<>(employeeService.updateEmp(emp), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{empId}")
	public ResponseEntity<String> deleteEmp(@PathVariable("empId") int empId) {
		return new ResponseEntity<String>(employeeService.deleteEmpById(empId), HttpStatus.OK);
	}
	@GetMapping("/findById/{empId}")
	public ResponseEntity<EmployeeStatus> findEmployeeByEmployeeId(@PathVariable(name="empId") Integer empId ){
		
		return new ResponseEntity<EmployeeStatus>(employeeService.getEmployeeById(empId), HttpStatus.OK);
		
	}
	


}
