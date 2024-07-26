package com.adt.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adt.hrms.model.InterviewHistory;

@Repository
public interface InterviewHistoryRepo extends JpaRepository<InterviewHistory, Integer> {

}
