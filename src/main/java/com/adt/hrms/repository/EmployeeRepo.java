package com.adt.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	@Query(value = "FROM Employee e WHERE lower(e.firstName) like lower(concat('%', :query,'%')) OR lower(e.lastName) like lower(concat('%', :query,'%'))")
	List<Employee> SearchByName(@Param("query") String name);

	@Query("FROM Employee e WHERE lower(e.email) like lower(concat('%', :query,'%'))")
	List<Employee> SearchByEmail(@Param("query") String email);

}
