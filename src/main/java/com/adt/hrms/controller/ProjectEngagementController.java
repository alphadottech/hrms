package com.adt.hrms.controller;

import com.adt.hrms.model.ProjectEngagement;
import com.adt.hrms.service.ProjectEngagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/engagement")
public class ProjectEngagementController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProjectEngagementService projectEngagementService;

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PostMapping("/saveProjectEngagement")
    public ResponseEntity<String> saveProjectEngagementDetail(
            @RequestBody ProjectEngagement projectEngagement, HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(projectEngagementService.saveProjectEngagementDetail(projectEngagement),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/allProjectEngagement")
    public ResponseEntity<List<ProjectEngagement>> allProjectEngagement(HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<List<ProjectEngagement>>(
                projectEngagementService.allProjectEngagement(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PutMapping("/updateProjectEngagement/{projectId}")
    public ResponseEntity<String> updateProjectDetail(@PathVariable("projectId") String projectId,
                                                      @RequestBody ProjectEngagement projectEngagement, HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(
                projectEngagementService.updateProjectDetail(projectId, projectEngagement),
                HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @GetMapping("/ProjectEngagementDetailById/{projectId}")
    public ResponseEntity<ProjectEngagement> getProjectDetailById(
            @PathVariable("projectId") String projectId, HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<ProjectEngagement>(
                projectEngagementService.getProjectDetailById(projectId), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @DeleteMapping("/DeleteProjectEngagement/{projectId}")
    public ResponseEntity<String> deleteProjectDetailById(@PathVariable("projectId") String projectId,
                                                          HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<String>(projectEngagementService.deleteProjectDetailById(projectId),
                HttpStatus.OK);
    }
}

