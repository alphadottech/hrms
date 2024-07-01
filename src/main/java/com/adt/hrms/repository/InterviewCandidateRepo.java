package com.adt.hrms.repository;

import com.adt.hrms.model.InterviewCandidateDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewCandidateRepo extends JpaRepository<InterviewCandidateDetails, Integer> {
    List<InterviewCandidateDetails> findCandidateDetailsByEmailId(String emailId);
    
    Optional<InterviewCandidateDetails >  findCandidateIdByEmailId(String emailId);
    
 
}
