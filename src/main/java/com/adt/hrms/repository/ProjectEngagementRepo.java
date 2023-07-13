package com.adt.hrms.repository;

import com.adt.hrms.model.ProjectEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectEngagementRepo extends JpaRepository<ProjectEngagement, Integer> {

	List<ProjectEngagement> findProjectEngagementByProjectName(String projectName);

	@Query(value = "select count(status) FROM ProjectEngagement")
	Integer findCount();

	Optional<ProjectEngagement> findByProjectId(String projectId);

	// JIRA no. :- HRMS-90 START---
	@Query(value = "FROM ProjectEngagement pe WHERE pe.engagedEmployee LIKE %:query% ")
	List<ProjectEngagement> SearchByEngagedEmployee(@Param("query") String empName);

	@Query(value = "FROM ProjectEngagement pe WHERE pe.projectName LIKE %:query% ")
	List<ProjectEngagement> SearchByProjectName(@Param("query") String projectName);

	@Query(value = "FROM ProjectEngagement pe WHERE pe.startDate LIKE %:startDate% AND pe.endDate LIKE %:endDate%")
	List<ProjectEngagement> findByProjectDate(String startDate, String endDate);
	// JIRA no. :- HRMS-90 END---
}
