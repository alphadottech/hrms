package com.alphadot.service;

import java.util.List;

import com.alphadot.model.Employee;
import com.alphadot.model.EmployeeStatus;

public interface EmployeeService {
	
	public List<Employee> getAllEmps();
	public Employee getEmp(Integer empId);
	public String saveEmp(Employee emp);
	public String deleteEmpById(Integer empId);
	public Employee updateEmp(Employee emp);
	public EmployeeStatus getEmployeeById(Integer empId);
}
