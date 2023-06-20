package com.adt.hrms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;

public interface EmployeeService {

	public List<Employee> getAllEmps();

	public Employee getEmp(Integer empId);

	public String saveEmp(Employee emp);

	public String deleteEmpById(Integer empId);

//	public String updateEmp(Integer empId, Employee emp,MultipartFile resume);
	
	//Jira no :- HRMS-77 start--
	public String updateEmp(Employee emp,MultipartFile resume)throws IOException ;
	
	public byte[] downloadImage(int id);
	//Jira no :- HRMS-77 End--
	
	public EmployeeStatus getEmployeeById(Integer empId);

	public Employee getEmployeeById(int empId);

	List<Employee> SearchEmployee(String query);

	List<Employee> SearchByEmailId(String query);


}
