package com.adt.hrms.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.repository.EmployeeStatusRepo;
import com.adt.hrms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private EmployeeStatusRepo employeeStatusRepo;

	// JIRA NO. :- HRMS-106(Bug Resolved) START---
	@Override
	public List<Employee> getAllEmps() {
		List<Employee> list = employeeRepo.findAll();
		return list;
	}
	// JIRA NO. :- HRMS-106(Bug Resolved) END---

	@Override
	public String saveEmp(Employee emp) {
		Optional<Employee> opt = employeeRepo.findById(emp.getEmployeeId());
		if (opt.isPresent())
			return "Employee with Id " + emp.getEmployeeId() + " is already avalable Pls Insert new ID....";
		return employeeRepo.save(emp).getEmployeeId() + " Employee is Saved";
	}

	@Override
	public String deleteEmpById(Integer empId) {
		Optional<Employee> opt = employeeRepo.findById(empId);
		if (opt.isPresent()) {
			employeeRepo.deleteById(empId);
			return empId + " has been Deleted";
		} else
			return "Invalid Employe Id :: " + empId;
	}

	@Override
	public EmployeeStatus getEmployeeById(Integer empId) {

		Optional<EmployeeStatus> opt = employeeStatusRepo.findById(empId);
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public Employee getEmployeeById(int empId) {
		Optional<Employee> emp = employeeRepo.findById(empId);
		return emp.get();
	}

	@Override
	@Transactional
	public List<Employee> SearchByName(String name) {
		List<Employee> emplist = employeeRepo.SearchByName(name);
		return emplist;
	}

	@Override
	@Transactional
	public List<Employee> SearchByEmail(String email) {
		List<Employee> emailemp = employeeRepo.SearchByEmail(email);
		return emailemp;
	}

	@Override
	public Employee getEmp(Integer empId) {
		Optional<Employee> opt = employeeRepo.findById(empId);
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public String updateEmp(Employee emp, MultipartFile resume, MultipartFile aadhar, MultipartFile pan)
			throws IOException {
		Optional<Employee> opt = employeeRepo.findById(emp.getEmployeeId());
		if (!opt.isPresent())
			return "Employee not found with id: " + emp.getEmployeeId();
		else
			opt.get().setAccountNumber(emp.getAccountNumber());
		opt.get().setBankName(emp.getBankName());
		opt.get().setDesignation(emp.getDesignation());
		opt.get().setDob(emp.getDob());
		opt.get().setFirstName(emp.getFirstName());
		opt.get().setGender(emp.getGender());
		opt.get().setIfscCode(emp.getIfscCode());
		opt.get().setJoinDate(emp.getJoinDate());
		opt.get().setLastName(emp.getLastName());
		opt.get().setMaritalStatus(emp.getMaritalStatus());
		opt.get().setMobileNo(emp.getMobileNo());
		opt.get().setSalary(emp.getSalary());
		// JIRA NO. :- HRMS-106(Bug Resolved) START---
		opt.get().setUserName(emp.getUserName());
		// JIRA NO. :- HRMS-106(Bug Resolved) END---
		opt.get().setIsActive(emp.getIsActive());
		// HRMS-77-Start
		opt.get().setResume(resume.getBytes());
		// HRMS-77-Ends
		// HRMS-78-Start
		opt.get().setAadharCard(aadhar.getBytes());
		opt.get().setPanCard(pan.getBytes());
		// HRMS-78-End
		return employeeRepo.save(opt.get()).getEmployeeId() + " Employee Updated Successfully";
	}

	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager START---
	@Override
	public byte[] downloadAadharCard(int id, HttpServletResponse resp) throws IOException {

		String headerKey = "Content-Disposition";

		Optional<Employee> dbImageData = employeeRepo.findById(id);
		String firstName = dbImageData.get().getFirstName();
		String lastName = dbImageData.get().getLastName();
		byte[] aadhar = dbImageData.get().getAadharCard();

		String headerValue = null;

		try {
			if (aadhar == null && headerValue == null) {
				System.out.println(" Aadhar Card is Not Available !!!");
			} else {

				resp.setContentType("image/jpeg");

				if (!firstName.isEmpty() && !lastName.isEmpty()) {
					headerValue = "attachment;filename=" + firstName + "_" + lastName + "_AadharCard.jpg";
					System.out.println(firstName + "_" + lastName + " : " + " Aadhar Card Downloaded Successfully !!!");
				} else if (firstName.isEmpty() && !lastName.isEmpty()) {
					headerValue = "attachment;filename=" + lastName + "_AadharCard.jpg";
					System.out.println(lastName + " : " + " Aadhar Card Downloaded Successfully !!!");
				} else if (!firstName.isEmpty() && lastName.isEmpty()) {
					headerValue = "attachment;filename=" + firstName + "_AadharCard.jpg";
					System.out.println(firstName + " : " + " Aadhar Card Downloaded Successfully !!!");
				} else if (firstName.isEmpty() && lastName.isEmpty()) {
					headerValue = "attachment;filename=_AadharCard.jpg";
					System.out.println(" Aadhar Card Downloaded Successfully !!!");
				}

				resp.setHeader(headerKey, headerValue);
				resp.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aadhar;
	}

	@Override
	public byte[] downloadPanCard(int id, HttpServletResponse resp) throws IOException {
		String headerKey = "Content-Disposition";

		Optional<Employee> dbImageData = employeeRepo.findById(id);
		String firstName = dbImageData.get().getFirstName();
		String lastName = dbImageData.get().getLastName();
		byte[] pan = dbImageData.get().getPanCard();

		String headerValue = null;

		try {
			if (pan == null && headerValue == null) {
				System.out.println(" Pan Card is Not Available !!!");
			} else {

				resp.setContentType("image/jpeg");

				if (!firstName.isEmpty() && !lastName.isEmpty()) {
					headerValue = "attachment;filename=" + firstName + "_" + lastName + "_PanCard.jpg";
					System.out.println(firstName + "_" + lastName + " : " + " Pan Card Downloaded Successfully !!!");
				} else if (firstName.isEmpty() && !lastName.isEmpty()) {
					headerValue = "attachment;filename=" + lastName + "_PanCard.jpg";
					System.out.println(lastName + " : " + " Pan Card Downloaded Successfully !!!");
				} else if (!firstName.isEmpty() && lastName.isEmpty()) {
					headerValue = "attachment;filename=" + firstName + "_PanCard.jpg";
					System.out.println(firstName + " : " + " Pan Card Downloaded Successfully !!!");
				} else if (firstName.isEmpty() && lastName.isEmpty()) {
					headerValue = "attachment;filename=_PanCard.jpg";
					System.out.println(" Pan Card Downloaded Successfully !!!");
				}

				resp.setHeader(headerKey, headerValue);
				resp.flushBuffer();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pan;
	}
	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager END---
}
