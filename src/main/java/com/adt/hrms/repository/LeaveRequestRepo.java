package com.adt.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adt.hrms.model.LeaveRequestModel;

public interface LeaveRequestRepo extends JpaRepository<LeaveRequestModel, Integer> {

	List<LeaveRequestModel> findByempid(Integer empid);

}
