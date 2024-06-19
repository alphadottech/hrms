package com.adt.hrms.repository;

import com.adt.hrms.model.ProjectEngagement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectEngagementRepo extends JpaRepository<ProjectEngagement, String> {

	List<ProjectEngagement> findProjectEngagementByProjectId(String projectId);
	boolean existsById(String projectId);

	Optional<ProjectEngagement> findByProjectId(String projectId);

//	@Query(value = "FROM ProjectEngagement pe WHERE pe.engagedEmployee LIKE %:query% ")
//	List<ProjectEngagement> SearchByEngagedEmployee(@Param("query") String empName);

	@Query(value = "FROM ProjectEngagement pe WHERE pe.contractor LIKE %:query% ")
	List<ProjectEngagement> SearchByProjectName(@Param("query") String contractor);

	@Query(value = "FROM ProjectEngagement pe WHERE pe.startDate LIKE %:startDate% AND pe.endDate LIKE %:endDate%")
	List<ProjectEngagement> findByProjectDate(String startDate, String endDate);

    Page<ProjectEngagement> findAll(Specification<ProjectEngagement> spec, Pageable pageable);
}
