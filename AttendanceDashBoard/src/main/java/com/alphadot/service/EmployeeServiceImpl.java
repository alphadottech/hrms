package com.alphadot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alphadot.model.Employee;
import com.alphadot.model.EmployeeStatus;
import com.alphadot.repository.EmployeeRepo;
import com.alphadot.repository.EmployeeStatusRepo;
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

	@Override
	public EmployeeStatus getEmployeeById(Integer empId) {
		
		Optional<EmployeeStatus> opt=employeeStatusRepo.findById(empId);
		if (opt.isPresent()) 
			return opt.get();
		else
			return null;	
	}


	
}
