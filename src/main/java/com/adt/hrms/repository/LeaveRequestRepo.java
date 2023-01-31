package com.adt.hrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.hrms.model.LeaveRequestModel;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequestModel, Integer> {

	Optional<LeaveRequestModel> findByempid(Integer empid);

}
