package com.adt.hrms.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adt.hrms.model.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Serializable> {
	
//	public Interview findById(int id);
	
	

}
