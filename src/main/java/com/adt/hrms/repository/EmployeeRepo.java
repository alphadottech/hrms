package com.adt.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	//Jira no :- HRMS-86 START--
	@Query(value = "FROM Employee e WHERE e.firstName LIKE %:query% OR e.lastName LIKE %:query%")
	List<Employee> SearchByName(@Param("query") String name);

	@Query(value = "FROM Employee e WHERE e.email LIKE %:query% ")
	List<Employee> SearchByEmail(@Param("query") String email);
	//Jira no :- HRMS-86 END--

}
