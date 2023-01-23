package com.adt.hrms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.EmployeeStatus;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.repository.EmployeeStatusRepo;
import com.adt.hrms.service.EmployeeService;

	
@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeStatusRepo employeeStatusRepo;
	
	@Override
	public List<Employee> getAllEmps() {
		List<Employee> list=employeeRepo.findAll();
		return list;
	}


	@Override
	public String saveEmp(Employee emp) {
		Optional<Employee> opt=employeeRepo.findById(emp.getEmpId());
		if(opt.isPresent())
			return "Employee with Id "+emp.getEmpId()+" is already avalable Pls Insert new ID....";
		return employeeRepo.save(emp).getEmpId()+" Employee is Saved";
	}

	@Override
	public String deleteEmpById(Integer empId) {
		Optional<Employee> opt=employeeRepo.findById(empId);
		if(opt.isPresent()) {
			employeeRepo.deleteById(empId);
			return empId+" has been Deleted";
		}
		else
			return "Invalid Employe Id :: "+empId;
	}
 
	@Override
	public String updateEmp(Employee emp) {		
		return employeeRepo.save(emp).getEmpId()+ " Details Updated Successfully";
	}

	@Override
	public EmployeeStatus getEmployeeById(Integer empId) {
		
		Optional<EmployeeStatus> opt=employeeStatusRepo.findById(empId);
		if (opt.isPresent()) 
			return opt.get();
		else
			return null;	
	}

	@Override
	public Employee getEmployeeById(int empId) {
		Employee emp = employeeRepo.findByEmpId(empId);
		return emp;
	}

	@Override
	public List<Employee> SearchEmployee(String query) {
		List<Employee> emplist = employeeRepo.SearchEmployee(query);
		return emplist;
	}

	@Override
	public List<Employee> SearchByEmailId(String query) {
		List<Employee> emailemp = employeeRepo.SearchByEmailId(query);
		return emailemp;
	}

	@Override
	public Employee getEmp(Integer empId) {
		Optional<Employee> opt = employeeRepo.findById(empId);
		if(opt.isPresent())
			return opt.get();
		else
			return null; 
	}
	

	


}
