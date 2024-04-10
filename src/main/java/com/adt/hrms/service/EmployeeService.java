package com.adt.hrms.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.request.EmployeeRequest;

public interface EmployeeService {

	// JIRA NO. :- HRMS-106(Bug Resolved) START---
	public Page<Employee> getAllEmps(int page, int size);
	// JIRA NO. :- HRMS-106(Bug Resolved) END---

	public Employee getEmp(Integer empId);

	public String saveEmp(Employee emp);

	public String deleteEmpById(Integer empId);

	public String updateEmp(EmployeeRequest empRequest, MultipartFile resume, MultipartFile aadhar, MultipartFile pan)
			throws IOException;

	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager START---
	public byte[] downloadAadharCard(int id, HttpServletResponse resp) throws IOException;

	public byte[] downloadPanCard(int id, HttpServletResponse resp) throws IOException;
	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager END---

	public EmployeeStatus getEmployeeById(Integer empId);

	public Employee getEmployeeById(int empId);

	public List<Employee> SearchByName(String name);

	public List<Employee> SearchByEmail(String email);

}
