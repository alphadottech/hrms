package com.adt.hrms.repository;

import com.adt.hrms.model.EmpPayrollDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpPayrollDetailsRepo extends JpaRepository<EmpPayrollDetails, Integer> {
    @Query(value = "select * from payroll_schema.emp_payroll_details where emp_id=?1", nativeQuery = true)
    Optional<EmpPayrollDetails> findByEmployeeId(Integer empId);
}