package com.adt.hrms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.adt.hrms.model.ProjectEngagement;
import com.adt.hrms.service.ProjectEngagementService;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/engagement")
public class ProjectEngagementController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProjectEngagementService projectEngagementService;

    @PreAuthorize("@auth.allow('SAVE_NEW_PROJECT_ENGAGEMENT_DETAILS')")
    @PostMapping("/saveProjectEngagement")
    public ResponseEntity<String> saveProjectEngagementDetail(@RequestBody ProjectEngagement projectEngagement,
                                                              HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(projectEngagementService.saveProjectEngagementDetail(projectEngagement),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_ALL_PROJECT_ENGAGEMENT_DETAILS')")
    @GetMapping("/allProjectEngagement")
    public ResponseEntity<List<ProjectEngagement>> allProjectEngagement(HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<List<ProjectEngagement>>(projectEngagementService.allProjectEngagement(),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('UPDATE_PROJECT_ENGAGEMENT_DETAILS_BY_PROJECT_ID')")
    @PutMapping("/updateProjectEngagement/{projectId}")
    public ResponseEntity<String> updateProjectDetail(@PathVariable("projectId") String projectId,
                                                      @RequestBody ProjectEngagement projectEngagement, HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(projectEngagementService.updateProjectDetail(projectId, projectEngagement),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_PROJECT_ENGAGEMENT_DETAILS_BY_PROJECT_ID')")
    @GetMapping("/ProjectEngagementDetailById/{projectId}")
    public ResponseEntity<ProjectEngagement> getProjectDetailById(@PathVariable("projectId") String projectId,
                                                                  HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<ProjectEngagement>(projectEngagementService.getProjectDetailById(projectId),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('DELETE_PROJECT_ENGAGEMENT_DETAILS_BY_PROJECT_ID')")
    @DeleteMapping("/DeleteProjectEngagement/{projectId}")
    public ResponseEntity<String> deleteProjectDetailById(@PathVariable("projectId") String projectId,
                                                          HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<String>(projectEngagementService.deleteProjectDetailById(projectId), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('SEARCH_PROJECT_ENGAGEMENT_DETAILS_BY_EMPLOYEE_NAME')")
    @GetMapping("/SearchByEngagedEmployee")
    public ResponseEntity<List<ProjectEngagement>> searchByEngagedEmployee(@RequestParam("query") String empName,
                                                                           HttpServletRequest request) {
        LOGGER.info("Employeeservice:engagement:searchByEngagedEmployee " + request.getRemoteHost());

        return new ResponseEntity<List<ProjectEngagement>>(projectEngagementService.SearchByEngagedEmployee(empName),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('SEARCH_PROJECT_ENGAGEMENT_DETAILS_BY_PROJECT_NAME')")
    @GetMapping("/SearchByProjectName")
    public ResponseEntity<List<ProjectEngagement>> searchByProjectName(@RequestParam("query") String projectName,
                                                                       HttpServletRequest request) {
        LOGGER.info("Employeeservice:engagement:searchByProjectName " + request.getRemoteHost());
        return new ResponseEntity<List<ProjectEngagement>>(projectEngagementService.SearchByProjectName(projectName),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('SEARCH_PROJECT_ENGAGEMENT_DETAILS_BY_PROJECT_DATE')")
    @GetMapping("/SearchProjectsByDate")
    public ResponseEntity<List<ProjectEngagement>> searchProjectsByDate(@RequestParam("startDate") String startDate,
                                                                        @RequestParam("endDate") String endDate, HttpServletRequest request) {
        LOGGER.info("Employeeservice:engagement:searchProjectsByDate " + request.getRemoteHost());
        return new ResponseEntity<List<ProjectEngagement>>(
                projectEngagementService.SearchProjectsByDate(startDate, endDate), HttpStatus.OK);
    }

}
