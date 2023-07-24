package com.adt.hrms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;

public interface EmployeeService {
	
	//JIRA NO. :- HRMS-106(Bug Resolved) START---
	public List<Employee> getAllEmps();
	//JIRA NO. :- HRMS-106(Bug Resolved) END---

	public Employee getEmp(Integer empId);

	public String saveEmp(Employee emp);

	public String deleteEmpById(Integer empId);

	//HRMS-77-Start
	public String updateEmp(Employee emp,MultipartFile resume,MultipartFile aadhar,MultipartFile pan)throws IOException ;
	//HRMS-77-End
	
	public EmployeeStatus getEmployeeById(Integer empId);

	public Employee getEmployeeById(int empId);

	public List<Employee> SearchByName(String name);

	public List<Employee> SearchByEmail(String email);

}
