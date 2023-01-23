package com.adt.hrms.repository;







import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	
//	@Query(value = "SELECT * FROM employee_details e WHERE " + "e.first_name LIKE CONCAT('%', :query, '%')" + "e.last_name LIKE CONCAT('%',:query,'%')", nativeQuery = true)
	
//	@Query("SELECT e FROM Employee e WHERE " + "e.fName LIKE CONCAT('%',:query,'%')" + "e.lName LIKE CONCAT('%',:query,'%')")
//	List<Employee> SearchEmployee(String query);
//	

	
	
	@Query(value = "SELECT * FROM employee_details e WHERE e.first_name LIKE %:query% OR e.last_name LIKE %:query%", nativeQuery = true)
	List<Employee> SearchEmployee(@Param("query") String query);
	
	@Query(value = "SELECT * FROM employee_details e WHERE e.email_id LIKE %:query% ", nativeQuery = true)
	List<Employee> SearchByEmailId(@Param("query") String query);
	
	Employee findByEmpId(int empId);

}
