package com.adt.hrms.service;

import com.adt.hrms.model.ProjectEngagement;

import java.util.List;

public interface ProjectEngagementService {

	String saveProjectEngagementDetail(ProjectEngagement projectEngagement);

	List<ProjectEngagement> allProjectEngagement();

	String updateProjectDetail(String projectId, ProjectEngagement projectEngagement);

	ProjectEngagement getProjectDetailById(String projectId);

	String deleteProjectDetailById(String projectId);

	public List<ProjectEngagement> SearchByEngagedEmployee(String empName);

	public List<ProjectEngagement> SearchByProjectName(String projectName);

	public List<ProjectEngagement> SearchProjectsByDate(String startDate, String endDate);

}
