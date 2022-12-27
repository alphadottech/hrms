package com.adt.hrms.repository;




import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.hrms.model.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	
	/*
	 * @Query(value="Select e from Employee e where e.mobileNo:%mobileNo% ") public
	 * List<Employee> findEmployeeDetailsByPhoneNo(Long mobileNo);
	 */
	Employee findByEmpId(int empId);
	


}
