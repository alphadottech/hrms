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
import com.adt.hrms.request.EmployeeUpdateByAdminDTO;
import com.adt.hrms.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    private static final int MAX_PAGE_SIZE = 50;
    private static final int DEFAULT_PAGE_SIZE = 10;



 
    // JIRA NO. :- HRMS-106(Bug Resolved) START---
    @Override
    public Page<Employee> getAllEmps(int page, int size) {
    	if (size <= 0 || size > MAX_PAGE_SIZE) {
            size = DEFAULT_PAGE_SIZE;
        }
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
        } else return "Invalid Employe Id :: " + empId;
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
        Optional<Employee> opt = employeeRepo.findById(emp.getEmployeeId());
        if (!opt.isPresent()) return "Employee not found with id: " + emp.getEmployeeId();
        if (emp.getDob() != null) opt.get().setDob(emp.getDob());
        if (emp.getFirstName() != null) opt.get().setFirstName(emp.getFirstName());
        if (emp.getGender() != null) opt.get().setGender(emp.getGender());
        if (emp.getLastName() != null) opt.get().setLastName(emp.getLastName());
        if (emp.getMaritalStatus() != null) opt.get().setMaritalStatus(emp.getMaritalStatus());
        if (emp.getMobileNo() != null) opt.get().setMobileNo(emp.getMobileNo());
        opt.get().setIsActive(emp.getIsActive());
        return employeeRepo.save(opt.get()) + " Employee Updated Successfully";
    }

    @Override
    public Employee getEmp(Integer empId) {
        Optional<Employee> opt = employeeRepo.findById(empId);
        if (opt.isPresent()) return opt.get();
        else return null;
    }

    @Override
    public String updateEmp(EmployeeRequest empRequest) {
        Optional<Employee> opt = employeeRepo.findById(empRequest.getEmployeeId());
        if (!opt.isPresent()) return "Employee not found with id: " + empRequest.getEmployeeId();
        if (empRequest.getDob() != null) opt.get().setDob(empRequest.getDob());
        if (empRequest.getFirstName() != null) opt.get().setFirstName(empRequest.getFirstName());
        if (empRequest.getGender() != null) opt.get().setGender(empRequest.getGender());
        if (empRequest.getLastName() != null) opt.get().setLastName(empRequest.getLastName());
        if (empRequest.getMaritalStatus() != null) opt.get().setMaritalStatus(empRequest.getMaritalStatus());
        if (empRequest.getMobileNo() != null) opt.get().setMobileNo(empRequest.getMobileNo());

        return employeeRepo.save(opt.get()).getEmployeeId() + " Employee Updated Successfully";
    }

//	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager START---
//	@Override
//	public byte[] downloadAadharCard(int id, HttpServletResponse resp) throws IOException {
//
//		String headerKey = "Content-Disposition";
//
//		Optional<Employee> dbImageData = employeeRepo.findById(id);
//		String firstName = dbImageData.get().getFirstName();
//		String lastName = dbImageData.get().getLastName();
//		byte[] aadhar = dbImageData.get().getAadharCard();
//
//		String headerValue = null;
//
//		try {
//			if (aadhar == null && headerValue == null) {
//				System.out.println(" Aadhar Card is Not Available !!!");
//			} else {
//
//				resp.setContentType("image/jpeg");
//
//				if (!firstName.isEmpty() && !lastName.isEmpty()) {
//					headerValue = "attachment;filename=" + firstName + "_" + lastName + "_AadharCard.jpg";
//					System.out.println(firstName + "_" + lastName + " : " + " Aadhar Card Downloaded Successfully !!!");
//				} else if (firstName.isEmpty() && !lastName.isEmpty()) {
//					headerValue = "attachment;filename=" + lastName + "_AadharCard.jpg";
//					System.out.println(lastName + " : " + " Aadhar Card Downloaded Successfully !!!");
//				} else if (!firstName.isEmpty() && lastName.isEmpty()) {
//					headerValue = "attachment;filename=" + firstName + "_AadharCard.jpg";
//					System.out.println(firstName + " : " + " Aadhar Card Downloaded Successfully !!!");
//				} else if (firstName.isEmpty() && lastName.isEmpty()) {
//					headerValue = "attachment;filename=_AadharCard.jpg";
//					System.out.println(" Aadhar Card Downloaded Successfully !!!");
//				}
//
//				resp.setHeader(headerKey, headerValue);
//				resp.flushBuffer();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return aadhar;
//	}

//	@Override
//	public byte[] downloadPanCard(int id, HttpServletResponse resp) throws IOException {
//		String headerKey = "Content-Disposition";
//
//		Optional<Employee> dbImageData = employeeRepo.findById(id);
//		String firstName = dbImageData.get().getFirstName();
//		String lastName = dbImageData.get().getLastName();
//		byte[] pan = dbImageData.get().getPanCard();
//
//		String headerValue = null;
//
//		try {
//			if (pan == null && headerValue == null) {
//				System.out.println(" Pan Card is Not Available !!!");
//			} else {
//
//				resp.setContentType("image/jpeg");
//
//				if (!firstName.isEmpty() && !lastName.isEmpty()) {
//					headerValue = "attachment;filename=" + firstName + "_" + lastName + "_PanCard.jpg";
//					System.out.println(firstName + "_" + lastName + " : " + " Pan Card Downloaded Successfully !!!");
//				} else if (firstName.isEmpty() && !lastName.isEmpty()) {
//					headerValue = "attachment;filename=" + lastName + "_PanCard.jpg";
//					System.out.println(lastName + " : " + " Pan Card Downloaded Successfully !!!");
//				} else if (!firstName.isEmpty() && lastName.isEmpty()) {
//					headerValue = "attachment;filename=" + firstName + "_PanCard.jpg";
//					System.out.println(firstName + " : " + " Pan Card Downloaded Successfully !!!");
//				} else if (firstName.isEmpty() && lastName.isEmpty()) {
//					headerValue = "attachment;filename=_PanCard.jpg";
//					System.out.println(" Pan Card Downloaded Successfully !!!");
//				}
//
//				resp.setHeader(headerKey, headerValue);
//				resp.flushBuffer();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return pan;
//	}
//	// JIRA NO. :- HRMS-108 Download Aadhaar & Pan Images in File Manager END---
@Override
public Page<Employee> searchEmployees(String firstName, String lastName, String email, Long mobileNo, String firstLetter, int page, int size) {
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
        spec = spec.or((root, query, cb) -> cb.like(cb.lower(root.get("firstName")), firstName.toLowerCase() + "%"));
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
        spec = spec.or((root, query, cb) -> cb.like(cb.lower(root.get("firstName")), firstLetter.toLowerCase() + "%"));
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
