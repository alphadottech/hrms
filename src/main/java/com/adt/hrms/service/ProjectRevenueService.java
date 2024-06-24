package com.adt.hrms.service;

import com.adt.hrms.model.ProjectRevenue;

import java.util.List;

public interface ProjectRevenueService {
    String saveProjectRevenueDetails(ProjectRevenue projectRevenue);

    String updateProjectRevenueDetails(ProjectRevenue projectRevenue);


    ProjectRevenue getProjectRevenueDetailsById(Integer id);

    String deleteProjectRevenueDetailById(Integer id);


    List<ProjectRevenue> allProjectRevenueDetails();

    List<ProjectRevenue> getProjectRevenueDetailsByProjectId(String projectId);
}
