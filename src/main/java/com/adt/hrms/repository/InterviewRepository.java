package com.adt.hrms.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adt.hrms.model.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Serializable> {
	
	
	//HRMS-56 START
	@Query("select i from Interview i where i.interviewId = ?1 and i.rounds = ?2")
	public Optional<Interview> getInterviewDetailByInterviewIdAndRound(int interviewId, int round);
	//HRMS-56 END

	//HRMS-92 -> START
	@Query(value = "SELECT i FROM Interview i WHERE i.candidateName like %:search%")
	public List<Interview> findByCandidateName(@Param("search") String candidateName);

	@Query(value = "SELECT i FROM Interview i WHERE i.source like %:search%")
	public List<Interview> findBySource(@Param("search") String source);

	@Query(value = "SELECT i FROM Interview i WHERE i.clientName like %:search%")
	public List<Interview> findByClientName(@Param("search") String clientName);
	//HRMS-92 ->END
	
	@Query(value = "select * from employee_schema.interview where candidate_id =?1 and rounds =?2",nativeQuery= true)
	public Optional<Interview>   findByCandidate(int candidateid, int round);

}
