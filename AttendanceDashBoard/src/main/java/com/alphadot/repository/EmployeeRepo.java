package com.alphadot.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alphadot.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Serializable> {
	
	/*
	 * @Query(value="Select e from Employee e where e.mobileNo:%mobileNo% ") public
	 * List<Employee> findEmployeeDetailsByPhoneNo(Long mobileNo);
	 */
	
	

}
