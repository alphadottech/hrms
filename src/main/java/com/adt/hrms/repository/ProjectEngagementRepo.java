package com.adt.hrms.repository;

import com.adt.hrms.model.ProjectEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectEngagementRepo extends JpaRepository<ProjectEngagement,Integer> {

    List<ProjectEngagement> findProjectEngagementByProjectName(String projectName);

    @Query(value = "select count(status) FROM ProjectEngagement")
    Integer findCount();

    Optional<ProjectEngagement> findByProjectId(String projectId);
}
