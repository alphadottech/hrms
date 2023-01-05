package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;

public interface EmployeeService {
	
	public List<Employee> getAllEmps();
	public Employee getEmp(Integer empId);
	public String saveEmp(Employee emp);
	public String deleteEmpById(Integer empId);
	public String updateEmp(Employee emp);
	public EmployeeStatus getEmployeeById(Integer empId);
	public Employee getEmployeeById(int empId);
	List<Employee> SearchEmployee(String query);
	List<Employee> SearchByEmailId(String query);
	
	
}
