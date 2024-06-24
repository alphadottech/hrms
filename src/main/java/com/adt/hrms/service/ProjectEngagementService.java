package com.adt.hrms.service;

import com.adt.hrms.model.ProjectEngagement;
import com.adt.hrms.model.ProjectRevenue;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectEngagementService {

	String saveProjectEngagementDetail(ProjectEngagement projectEngagement);

	List<ProjectEngagement> allProjectEngagement();

	String updateProjectDetail(String projectId, ProjectEngagement projectEngagement);

	ProjectEngagement getProjectDetailById(String projectId);

	String deleteProjectDetailById(String projectId);

	public List<ProjectEngagement> SearchByProjectName(String contractor);

	public List<ProjectEngagement> SearchProjectsByDate(String startDate, String endDate);

	Page<ProjectEngagement> searchProjectEngagementbyFields(String primaryResource, String secondaryResource, String startDate, String endDate, int page, int size);

}
