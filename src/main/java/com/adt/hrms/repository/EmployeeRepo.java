package com.adt.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	@Query(value = "FROM Employee e WHERE e.firstName LIKE %:query% OR e.lastName LIKE %:query%")
	List<Employee> SearchEmployee(@Param("query") String query);

	@Query(value = "SELECT * FROM employee_details e WHERE e.email_id LIKE %:query% ", nativeQuery = true)
	List<Employee> SearchByEmailId(@Param("query") String query);

	Employee findByEmpId(int empId);

	List<Employee> findByFirstNameContaining(@Param("name") String name);
}
