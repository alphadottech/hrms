package com.adt.hrms.service;

import org.springframework.data.domain.Page;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.request.EmployeeRequest;
import com.adt.hrms.request.EmployeeUpdateByAdminDTO;

public interface EmployeeService {

	// JIRA NO. :- HRMS-106(Bug Resolved) START---
	public Page<Employee> getAllEmps(int page, int size);
	// JIRA NO. :- HRMS-106(Bug Resolved) END---

	public Employee getEmp(Integer empId);

	public String saveEmp(Employee emp);

	public String deleteEmpById(Integer empId);

	public String updateEmp(EmployeeRequest empRequest);

	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager START---
//	public byte[] downloadAadharCard(int id, HttpServletResponse resp) throws IOException;
//
//	public byte[] downloadPanCard(int id, HttpServletResponse resp) throws IOException;
//	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager END---

	public EmployeeStatus getEmployeeById(Integer empId);

	public Employee getEmployeeById(int empId);

	public Page<Employee> SearchByName(String name,int page,int size);

	public Page<Employee> SearchByEmail(String email,int page,int size);

	public String updateEmpById(EmployeeUpdateByAdminDTO emp);
}
