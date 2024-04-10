package com.adt.hrms.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.repository.EmployeeStatusRepo;
import com.adt.hrms.request.EmployeeRequest;
import com.adt.hrms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private EmployeeStatusRepo employeeStatusRepo;

	// JIRA NO. :- HRMS-106(Bug Resolved) START---
	@Override
	public Page<Employee> getAllEmps(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return employeeRepo.findAll(pageable);
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
	public String updateEmp(EmployeeRequest empRequest, MultipartFile resume, MultipartFile aadhar, MultipartFile pan)
			throws IOException {
		Optional<Employee> opt = employeeRepo.findById(empRequest.getEmployeeId());
		if (!opt.isPresent())
			return "Employee not found with id: " + empRequest.getEmployeeId();
		else if (empRequest.getAccountNumber() != null)
			opt.get().setAccountNumber(empRequest.getAccountNumber());
		if (empRequest.getBankName() != null)
			opt.get().setBankName(empRequest.getBankName());
		if (empRequest.getDesignation() != null)
			opt.get().setDesignation(empRequest.getDesignation());
		if (empRequest.getDob() != null)
			opt.get().setDob(empRequest.getDob());
		if (empRequest.getFirstName() != null)
			opt.get().setFirstName(empRequest.getFirstName());
		if (empRequest.getGender() != null)
			opt.get().setGender(empRequest.getGender());
		if (empRequest.getIfscCode() != null)
			opt.get().setIfscCode(empRequest.getIfscCode());
		if (empRequest.getJoinDate() != null)
			opt.get().setJoinDate(empRequest.getJoinDate());
		if (empRequest.getLastName() != null)
			opt.get().setLastName(empRequest.getLastName());
		if (empRequest.getMaritalStatus() != null)
			opt.get().setMaritalStatus(empRequest.getMaritalStatus());
		if (empRequest.getMobileNo() != null)
			opt.get().setMobileNo(empRequest.getMobileNo());
		if (empRequest.getSalary() != null)
			opt.get().setSalary(empRequest.getSalary());
		if (empRequest.getUserName() != null)
			// JIRA NO. :- HRMS-106(Bug Resolved) START---
			opt.get().setUserName(empRequest.getUserName());
		// JIRA NO. :- HRMS-106(Bug Resolved) END---
		if (empRequest.getIsActive() != null)
			opt.get().setIsActive(empRequest.getIsActive());
		// HRMS-77-Start
		if (resume != null && !resume.isEmpty())
			opt.get().setResume(resume.getBytes());
		// HRMS-77-Ends
		// HRMS-78-Start
		if (aadhar != null && !aadhar.isEmpty())
			opt.get().setAadharCard(aadhar.getBytes());
		if (pan != null && !pan.isEmpty())
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
