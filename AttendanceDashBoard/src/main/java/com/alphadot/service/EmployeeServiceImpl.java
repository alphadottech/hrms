package com.alphadot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alphadot.model.Employee;
import com.alphadot.repository.EmployeeRepo;
@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	@Override
	public List<Employee> getAllEmps() {
		return employeeRepo.findAll();
	}

	@Override
	public Employee getEmp(Integer empId) {
		Optional<Employee> opt=employeeRepo.findById(empId);
		if (opt.isPresent()) 
			return opt.get();
		else
			return null;
		
	}
	
	@Override
	public String saveEmp(Employee emp) {
		Optional<Employee> opt=employeeRepo.findById(emp.getEmpId());
		if(opt.isPresent())
			return "Employee with Id "+emp.getEmpId()+" is alredy avalable Pls Insert new ID....";
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
	public Employee updateEmp(Employee emp) {		
		return employeeRepo.save(emp);
	}
}
