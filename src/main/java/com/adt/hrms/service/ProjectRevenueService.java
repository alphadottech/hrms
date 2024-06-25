package com.adt.hrms.service;

import com.adt.hrms.model.ProjectRevenue;

import java.util.List;
import java.util.Optional;

public interface ProjectRevenueService {
    String saveOrUpdateProjectRevenue(ProjectRevenue projectRevenue);

    String saveProjectRevenueDetails(ProjectRevenue projectRevenue);

  //  String updateProjectRevenueDetails(ProjectRevenue projectRevenue);


    ProjectRevenue getProjectRevenueDetailsById(Integer id);

    String deleteProjectRevenueDetailById(Integer id);


    List<ProjectRevenue> allProjectRevenueDetails();

    List<ProjectRevenue> getProjectRevenueDetailsByProjectId(String projectId);
    Optional<ProjectRevenue> getProjectRevenueDetails(String projectId, String year, String month);

  //  String saveOrUpdateProjectRevenue(ProjectRevenue projectRevenue);
}
