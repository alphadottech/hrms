package com.adt.hrms.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adt.hrms.model.Employee;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.request.EmployeeRequest;
import com.adt.hrms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	private static final int MAX_PAGE_SIZE = 50;
	private static final int DEFAULT_PAGE_SIZE = 10;

	@Override
	public Page<Employee> getAllEmps(int page, int size) {
		if (size <= 0 || size > MAX_PAGE_SIZE) {
			size = DEFAULT_PAGE_SIZE;
		}
		Pageable pageable = PageRequest.of(page, size);
		return employeeRepo.findByIsActiveTrue(pageable);
	}

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
			if(opt.get().getAdtId()!=null&&!opt.get().getAdtId().isEmpty()) {
			Employee employee = opt.get();
			employee.setIsActive(false);
			employeeRepo.save(employee);
			return empId + " deleted Successfully";
			}else {
				return "ADT ID is null or Invalid";
			}
		} else
			return "Invalid Employee Id : " + empId;
	}

	@Override
	public Employee getEmployeeById(int empId) {
		Optional<Employee> emp = employeeRepo.findById(empId);
		return emp.get();
	}

	@Override
	@Transactional
	public Page<Employee> SearchByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Employee> emplist = employeeRepo.SearchByName(name, pageable);
		return emplist;
	}

	@Override
	@Transactional
	public Page<Employee> SearchByEmail(String email, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Employee> emailemp = employeeRepo.SearchByEmail(email, pageable);
		return emailemp;
	}

	@Override
	public String updateEmpById(Employee emp) {
		Employee existEmployee = employeeRepo.findById(emp.getEmployeeId())
				.orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + emp.getEmployeeId()));

		if (emp.getDob() != null) {
			existEmployee.setDob(emp.getDob());
		}
		if (emp.getFirstName() != null) {
			existEmployee.setFirstName(emp.getFirstName());
		}
		if (emp.getGender() != null) {
			existEmployee.setGender(emp.getGender());
		}
		if (emp.getLastName() != null) {
			existEmployee.setLastName(emp.getLastName());
		}
		if (emp.getMaritalStatus() != null) {
			existEmployee.setMaritalStatus(emp.getMaritalStatus());
		}
		if (emp.getMobileNo() != null) {
			existEmployee.setMobileNo(emp.getMobileNo());
		}
		if (emp.getIsActive() != null) {
			existEmployee.setIsActive(emp.getIsActive());
		}
		if (emp.getEmployeeType() != null) {
			existEmployee.setEmployeeType(emp.getEmployeeType());
		}

		employeeRepo.save(existEmployee);

		return "Employee with id " + emp.getEmployeeId() + " updated successfully";
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
	public String updateEmp(EmployeeRequest empRequest) {
		Optional<Employee> opt = employeeRepo.findById(empRequest.getEmployeeId());
		if (!opt.isPresent())
			return "Employee not found with id: " + empRequest.getEmployeeId();
		if (empRequest.getDob() != null)
			opt.get().setDob(empRequest.getDob());
		if (empRequest.getFirstName() != null)
			opt.get().setFirstName(empRequest.getFirstName());
		if (empRequest.getGender() != null)
			opt.get().setGender(empRequest.getGender());
		if (empRequest.getLastName() != null)
			opt.get().setLastName(empRequest.getLastName());
		if (empRequest.getMaritalStatus() != null)
			opt.get().setMaritalStatus(empRequest.getMaritalStatus());
		if (empRequest.getMobileNo() != null)
			opt.get().setMobileNo(empRequest.getMobileNo());

		return employeeRepo.save(opt.get()).getEmployeeId() + " Employee Updated Successfully";
	}

	@Override
	public Page<Employee> searchEmployees(String firstName, String lastName, String email, Long mobileNo,
			String firstLetter, int page, int size) {
		if (email == "") {
			throw new IllegalArgumentException("Invalid email value");
		} else if (email != null && !isValidEmail(email)) {
			throw new IllegalArgumentException("Invalid email format");

		}
		if (firstName != null && !firstName.isEmpty() && containsDigit(firstName)) {
			throw new IllegalArgumentException("First name cannot contain digits or numbers");
		} else if (firstName == "") {
			throw new IllegalArgumentException("First name cannot be empty");
		}
		if (lastName != null && !lastName.isEmpty() && containsDigit(lastName)) {
			throw new IllegalArgumentException("Last name cannot contain digits or numbers");
		} else if (lastName == "") {
			throw new IllegalArgumentException("Last name cannot be empty");
		}
		if (mobileNo != null) {
			if (String.valueOf(mobileNo).length() < 10) {
				throw new IllegalArgumentException("Mobile number must not be less than 10 digits");
			} else if (String.valueOf(mobileNo).length() > 10) {
				throw new IllegalArgumentException("Mobile number must not be greater than 10 digits");
			}
		} else if (mobileNo == null && firstName == null && lastName == null && firstLetter == null && email == null) {
			throw new IllegalArgumentException("Mobile number must not be null");
		}
		Pageable pageable = PageRequest.of(page, size);
		Specification<Employee> spec = Specification.where(null);

		if (firstName != null && !firstName.isEmpty()) {
			spec = spec
					.or((root, query, cb) -> cb.like(cb.lower(root.get("firstName")), firstName.toLowerCase() + "%"));
		}
		if (lastName != null && !lastName.isEmpty()) {
			spec = spec.or((root, query, cb) -> cb.like(cb.lower(root.get("lastName")), lastName.toLowerCase() + "%"));
		}
		if (email != null && !email.isEmpty()) {
			spec = spec.or((root, query, cb) -> cb.equal(root.get("email"), email));
		}
		if (mobileNo != null) {
			spec = spec.or((root, query, cb) -> cb.equal(root.get("mobileNo"), mobileNo));
		}

		// Add criteria for searching by first letter of first name
		if (firstLetter != null && !firstLetter.isEmpty()) {
			spec = spec
					.or((root, query, cb) -> cb.like(cb.lower(root.get("firstName")), firstLetter.toLowerCase() + "%"));
		}

		return employeeRepo.findAll(spec, pageable);
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(emailRegex);
	}

	private boolean containsDigit(String str) {
		for (char c : str.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;

			}
		}
		return false;
	}
}
