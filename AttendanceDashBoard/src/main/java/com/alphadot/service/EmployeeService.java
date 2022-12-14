package com.alphadot.service;

import java.util.List;

import com.alphadot.model.Employee;

public interface EmployeeService {
	public List<Employee> getAllEmps();
	public Employee getEmp(Integer empId);
	public String saveEmp(Employee emp);
	public String deleteEmpById(Integer empId);
	public Employee updateEmp(Employee emp);
}
