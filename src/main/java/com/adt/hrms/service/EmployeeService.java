package com.adt.hrms.service;

import org.springframework.data.domain.Page;

import com.adt.hrms.model.Employee;
import com.adt.hrms.request.EmployeeRequest;
import com.adt.hrms.request.EmployeeUpdateByAdminDTO;

public interface EmployeeService {

	public Page<Employee> getAllEmps(int page, int size);

	public Employee getEmp(Integer empId);

	public String saveEmp(Employee emp);

	public String deleteEmpById(String empId);

	public String updateEmp(EmployeeRequest empRequest);

	public Employee getEmployeeById(int empId);

	public Page<Employee> SearchByName(String name,int page,int size);

	public Page<Employee> SearchByEmail(String email,int page,int size);

	public String updateEmpById(Employee emp);

	Page<Employee> searchEmployees(String firstName, String lastName, String email, Long mobileNo, String firstLetter, int page, int size);


}
