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
    public String saveOrUpdateProjectRevenue(ProjectRevenue projectRevenue) {
        return "";
    }

    @Override
    public String saveProjectRevenueDetails(ProjectRevenue projectRevenue) {
        // Validate project revenue details
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

        // Check if project engagement exists
        Optional<ProjectEngagement> projectDetails = projectEngagementRepo.findByProjectId(projectRevenue.getProjectEngagement().getProjectId());
        if (projectDetails.isPresent()) {
            // Check if project revenue already exists for the given month and year
            Optional<ProjectRevenue> existingRevenue = projectRevenueRepo.findByProjectEngagementProjectIdAndYearAndMonth(
                    projectRevenue.getProjectEngagement().getProjectId(),
                    projectRevenue.getYear(),
                    projectRevenue.getMonth());

            if (!existingRevenue.isPresent()) {
                // Save new project revenue details
                projectRevenueRepo.save(projectRevenue);
                return "Project Revenue Saved Successfully for Id: " + projectRevenue.getProjectEngagement().getProjectId();
            } else {
                // Update existing project revenue details
                ProjectRevenue revenue = existingRevenue.get();
                revenue.setProjectRevenue(projectRevenue.getProjectRevenue());
                revenue.setResourceExpense(projectRevenue.getResourceExpense());
                projectRevenueRepo.save(revenue);
                return "Project revenue details updated";
            }
        } else {
            return "Project engagement not found";
        }
    }




    //    @Override
//    public String updateProjectRevenueDetails(ProjectRevenue projectRevenue) {
//
//        if (!ProjectEngagementUtility.validateCTC(projectRevenue.getProjectRevenue())) {
//            throw new IllegalArgumentException("Invalid Revenue Details");
//        }
//        if (!ProjectEngagementUtility.validateCTC(projectRevenue.getResourceExpense())) {
//            throw new IllegalArgumentException("Invalid Expense Details");
//        }
//        if (!ProjectEngagementUtility.validateEmployee(projectRevenue.getMonth())) {
//            throw new IllegalArgumentException("Invalid Month Details");
//        }
//      if (!AssetUtility.validateYear(projectRevenue.getYear())) {
//            throw new IllegalArgumentException("Invalid Year Details");
//        }
//        Optional<ProjectRevenue> ProjectRevenueOptional = projectRevenueRepo.findById(projectRevenue.getId());
//
//
//        if (ProjectRevenueOptional.isPresent()) {
//            ProjectRevenueOptional.get().setYear(projectRevenue.getYear());
//            ProjectRevenueOptional.get().setMonth(projectRevenue.getMonth());;
//            ProjectRevenueOptional.get().setProjectRevenue(projectRevenue.getProjectRevenue());
//            ProjectRevenueOptional.get().setResourceExpense(projectRevenue.getResourceExpense());
//            projectRevenueRepo.save(ProjectRevenueOptional.get());
//            return "Project revenue details updated";
//        } else {
//            return "Project revenue details not found";
//        }
//    }
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
    @Override
    public Optional<ProjectRevenue> getProjectRevenueDetails(String projectId, String year, String month) {
        return projectRevenueRepo.findByProjectEngagementProjectIdAndYearAndMonth(projectId, year, month);
    }
}



