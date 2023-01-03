package com.adt.hrms.repository;






import org.springframework.data.jpa.repository.JpaRepository;


import com.adt.hrms.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	

	Employee findByEmpId(int empId);
	


}
