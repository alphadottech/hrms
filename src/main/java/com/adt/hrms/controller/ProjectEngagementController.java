package com.adt.hrms.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.adt.hrms.model.ProjectRevenue;
import com.adt.hrms.service.ProjectRevenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ProjectRevenueService projectRevenueService;

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

    @PreAuthorize("@auth.allow('SEARCH_PROJECT_ENGAGEMENT_DETAILS_BY_PROJECT_NAME')")
    @GetMapping("/SearchByProjectName")
    public ResponseEntity<List<ProjectEngagement>> searchByProjectName(@RequestParam("query") String contractor,
                                                                       HttpServletRequest request) {
        LOGGER.info("Employeeservice:engagement:searchByProjectName " + request.getRemoteHost());
        return new ResponseEntity<List<ProjectEngagement>>(projectEngagementService.SearchByProjectName(contractor),
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


  @PreAuthorize("@auth.allow('SAVE_PROJECT_REVENUE')")
  @PostMapping("/saveProjectRevenue")
  public ResponseEntity<String> saveOrUpdateProjectRevenue(@RequestBody ProjectRevenue projectRevenue,
                                                           HttpServletRequest request) {
      LOGGER.info("API Call From IP: " + request.getRemoteHost());

      try {
          String response = projectRevenueService.saveProjectRevenueDetails(projectRevenue);
          return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (IllegalArgumentException e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
      }
  }


    @PreAuthorize("@auth.allow('GET_PROJECT_REVENUE_DETAILS_BY_ID')")
    @GetMapping("/getProjectRevenueDetailById/{id}")
    public ResponseEntity<ProjectRevenue> getProjectRevenueDetailById(@PathVariable("id") Integer id,
                                                                      HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<ProjectRevenue>(projectRevenueService.getProjectRevenueDetailsById(id),
                HttpStatus.OK);
    }
    @PreAuthorize("@auth.allow('DELETE_PROJECT_REVENUE_DETAILS_BY_ID')")
    @DeleteMapping("/deleteProjectRevenue/{id}")
    public ResponseEntity<String> deleteProjectRevenueById(@PathVariable("id") Integer id,
                                                           HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<String>(projectRevenueService.deleteProjectRevenueDetailById(id), HttpStatus.OK);
    }
    @PreAuthorize("@auth.allow('GET_ALL_PROJECT_REVENUE_DETAILS')")
    @GetMapping("/getAllProjectRevenue")
    public ResponseEntity<List<ProjectRevenue>> allProjectRevenueList(HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<List<ProjectRevenue>>(projectRevenueService.allProjectRevenueDetails(),
                HttpStatus.OK);
    }
    @PreAuthorize("@auth.allow('SEARCH_PROJECT_ENGAGEMENT_DETAILS_BY_FIELDS')")
    @GetMapping("/getProjectEngagementByFields")
    public ResponseEntity<Page<ProjectEngagement>>searchProjectEngagementByFields(
            @RequestParam(value="primaryResource" ,required = false) String primaryResource,
            @RequestParam(value="secondaryResource" ,required = false) String secondaryResource,
            @RequestParam(value="startDate" ,required = false) String startDate,
            @RequestParam(value="endDate" ,required = false) String endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
        LOGGER.info("Project Engagement: Searching for ProjectEngagement");
        Page<ProjectEngagement> searchResult = projectEngagementService.searchProjectEngagementbyFields(primaryResource,secondaryResource,startDate, endDate,page, size);
        return ResponseEntity.ok(searchResult);
    }



    @PreAuthorize("@auth.allow('GET_PROJECT_REVENUE_DETAILS_BY_PROJECT_ID')")
    @GetMapping("/getRevenueDetailsByprojectId/{projectId}")
    public List<ProjectRevenue> getProjectRevenueByProjectId(@PathVariable String projectId) {
        return projectRevenueService.getProjectRevenueDetailsByProjectId(projectId);
    }

    @PreAuthorize("@auth.allow('GET_ALL_PROJECT_ENGAGEMENT_TO_EXCEL')")
    @GetMapping("/getAllProjectEngagementExportToExcel")
    public ResponseEntity<Resource> getAllProjectEngagementDetailsExportToExcel() throws IOException {

        String filename = "alphadot_Project_Engagement_data.xlsx";
        ByteArrayInputStream newdata = projectEngagementService.getExcelData();
        InputStreamResource file = new InputStreamResource(newdata);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
    @PreAuthorize("@auth.allow('GET_PROJECT_REVENUE_BY_YEAR_MONTH')")
    @GetMapping("getProjectRevenue/{projectId}/{year}/{month}")
    public Optional<ProjectRevenue> getProjectRevenueDetails(
            @PathVariable String projectId,
            @PathVariable String year,
            @PathVariable String month) {
        return projectRevenueService.getProjectRevenueDetails(projectId, year, month);
    }
}
