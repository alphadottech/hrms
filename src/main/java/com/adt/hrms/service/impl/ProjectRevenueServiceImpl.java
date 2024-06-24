package com.adt.hrms.service.impl;

import com.adt.hrms.model.ProjectEngagement;
import com.adt.hrms.model.ProjectRevenue;
import com.adt.hrms.repository.ProjectEngagementRepo;
import com.adt.hrms.repository.ProjectRevenueRepo;
import com.adt.hrms.service.ProjectRevenueService;
import com.adt.hrms.util.AssetUtility;
import com.adt.hrms.util.ProjectEngagementUtility;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectRevenueServiceImpl implements ProjectRevenueService {

    @Autowired
    ProjectRevenueRepo projectRevenueRepo;

    @Autowired
    ProjectEngagementRepo projectEngagementRepo;

    @Override
    public String saveProjectRevenueDetails(ProjectRevenue projectRevenue) {
        if (!ProjectEngagementUtility.validateCTC(projectRevenue.getProjectRevenue())) {
            throw new IllegalArgumentException("Invalid Revenue Details");
        }
        if (!ProjectEngagementUtility.validateCTC(projectRevenue.getResourceExpense())) {
            throw new IllegalArgumentException("Invalid Expense Details");
        }
        if (!ProjectEngagementUtility.validateEmployee(projectRevenue.getMonth())) {
            throw new IllegalArgumentException("Invalid Month Details");
        }
        if (!AssetUtility.validateRAM(projectRevenue.getYear())) {
            throw new IllegalArgumentException("Invalid Year Details");
        }
        if (!AssetUtility.validateYear(projectRevenue.getYear())) {
            throw new IllegalArgumentException("Invalid Year Details");
        }
        Optional<ProjectEngagement> projectdetails = projectEngagementRepo.findByProjectId(projectRevenue.getProjectEngagement().getProjectId());
        if (projectdetails.isPresent()) {
            return "Project Revenue Saved Successfully for Id : " + projectRevenueRepo.save(projectRevenue).getProjectEngagement().getProjectId();
        }
        return "Some issue in input. Cannot save revenue details";
    }

    @Override
    public String updateProjectRevenueDetails(ProjectRevenue projectRevenue) {

        if (!ProjectEngagementUtility.validateCTC(projectRevenue.getProjectRevenue())) {
            throw new IllegalArgumentException("Invalid Revenue Details");
        }
        if (!ProjectEngagementUtility.validateCTC(projectRevenue.getResourceExpense())) {
            throw new IllegalArgumentException("Invalid Expense Details");
        }
        if (!ProjectEngagementUtility.validateEmployee(projectRevenue.getMonth())) {
            throw new IllegalArgumentException("Invalid Month Details");
        }
      if (!AssetUtility.validateYear(projectRevenue.getYear())) {
            throw new IllegalArgumentException("Invalid Year Details");
        }
        Optional<ProjectRevenue> ProjectRevenueOptional = projectRevenueRepo.findById(projectRevenue.getId());


        if (ProjectRevenueOptional.isPresent()) {
            ProjectRevenueOptional.get().setYear(projectRevenue.getYear());
            ProjectRevenueOptional.get().setMonth(projectRevenue.getMonth());;
            ProjectRevenueOptional.get().setProjectRevenue(projectRevenue.getProjectRevenue());
            ProjectRevenueOptional.get().setResourceExpense(projectRevenue.getResourceExpense());
            projectRevenueRepo.save(ProjectRevenueOptional.get());
            return "Project revenue details updated";
        } else {
            return "Project revenue details not found";
        }
    }
    @Override
    public ProjectRevenue getProjectRevenueDetailsById(Integer id) {
        Optional<ProjectRevenue> projectRevenue=projectRevenueRepo.findById(id);
        if(projectRevenue.isPresent()){
            return projectRevenue.get();
        }
        else{
            throw new EntityNotFoundException("ProjectRevenue with ID  " + id +  "  is not found");
        }
    }
    @Override
    public String deleteProjectRevenueDetailById(Integer id) {
        Optional<ProjectRevenue> projectRevenue = projectRevenueRepo.findById(id);
        if (projectRevenue.isPresent()) {
            projectRevenueRepo.deleteById(id);
            return "Project Revenue with ID " + id + " has been deleted successfully";
        }
        else {
            throw new EntityNotFoundException("ProjectRevenue with ID " + id + " is not found");
        }
    }
    @Override
    public List<ProjectRevenue> allProjectRevenueDetails() {
        List<ProjectRevenue> projectlist=projectRevenueRepo.findAll();
        return projectlist;
    }

    public List<ProjectRevenue> getProjectRevenueDetailsByProjectId(String projectId) {
        Optional<ProjectEngagement> projectEngagement = projectEngagementRepo.findByProjectId(projectId);
        if (projectEngagement.isPresent()) {
            return projectRevenueRepo.findByProjectEngagementProjectId(projectId);
        } else {
            throw new EntityNotFoundException("Project Engagement Not Found");
        }
    }
}



