package com.adt.hrms.repository;

import com.adt.hrms.model.ProjectEngagement;
import com.adt.hrms.model.ProjectRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRevenueRepo extends JpaRepository<ProjectRevenue,Integer> {

    public List<ProjectRevenue> findByProjectEngagement(ProjectEngagement projectEngagement);

    Optional<ProjectRevenue> findByProjectEngagement(Optional<ProjectEngagement> projectEngagement);

    List<ProjectRevenue> findByProjectEngagementProjectId(String projectId);
//    List<ProjectRevenue> findByProjectEngagementProjectIdAndYearAndMonth(String projectId, String year, String month);

    Optional<ProjectRevenue> findByProjectEngagementProjectIdAndYearAndMonth(String projectId, String year, String month);
}
