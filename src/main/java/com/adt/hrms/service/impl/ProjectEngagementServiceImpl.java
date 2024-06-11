package com.adt.hrms.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.ProjectEngagement;
import com.adt.hrms.repository.ProjectEngagementRepo;
import com.adt.hrms.service.ProjectEngagementService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectEngagementServiceImpl implements ProjectEngagementService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public ProjectEngagementRepo projectEngagementRepo;

	@Autowired
	private MessageSource messageSource;

	private final Object lock = new Object();

	@Override
	public String saveProjectEngagementDetail(ProjectEngagement projectEngagement) {
		List<ProjectEngagement> opt = projectEngagementRepo
				.findProjectEngagementByProjectId(projectEngagement.getProjectId());

		if (opt.size() > 0) {
			return "Project With Id: " + opt.get(0).getProjectId() + " is Already Present";
		}
		String projectId;

		synchronized (lock) {
			long count = projectEngagementRepo.count();
			projectId = "PROJ0" + (count + 1);
			while (projectEngagementRepo.existsById(projectId)) {
				count++;
				projectId = "PROJ0" + (count + 1);
			}
		}
		projectEngagement.setProjectId(projectId);

		return "Project With Id:: " + projectEngagementRepo.save(projectEngagement).getProjectId()
				+ " is Successfully Saved";

	}

	@Override
	public List<ProjectEngagement> allProjectEngagement() {
		List<ProjectEngagement> projectEngagementsDetail = projectEngagementRepo.findAll();
		return projectEngagementsDetail;
	}

	@Override
	public String updateProjectDetail(String projectId, ProjectEngagement projectEngagement) {
		Optional<ProjectEngagement> projectDetails = projectEngagementRepo.findByProjectId(projectId);
		if (projectDetails.isEmpty())
			return "Project With Id: " + projectId + " is Not Present";

		projectDetails.get().setProjectName(projectEngagement.getProjectName());
		projectDetails.get().setProjectDescription(projectEngagement.getProjectDescription());
		projectDetails.get().setEngagedEmployee(projectEngagement.getEngagedEmployee());
		projectDetails.get().setStartDate(projectEngagement.getStartDate());
		projectDetails.get().setEndDate(projectEngagement.getEndDate());
		projectDetails.get().setStatus(projectEngagement.isStatus());

		return "Project With Id: " + projectEngagementRepo.save(projectDetails.get()).getProjectId()
				+ " is Successfully Updated";
	}

	@Override
	public ProjectEngagement getProjectDetailById(String projectId) {
		Optional<ProjectEngagement> projectDetail = projectEngagementRepo.findByProjectId(projectId);
		if (projectDetail.isEmpty()) {
			String message = messageSource.getMessage("api.error.data.not.found.id", null, Locale.ENGLISH);
			LOGGER.error(message = message + projectId);
			throw new EntityNotFoundException(message);
		}
		return projectDetail.get();
	}

	@Override
	public String deleteProjectDetailById(String projectId) {
		if (projectEngagementRepo.findByProjectId(projectId).isPresent()) {
			Optional<ProjectEngagement> projectDetails = projectEngagementRepo.findByProjectId(projectId);
			projectEngagementRepo.deleteById(projectId);
			return "Project With Id: " + projectId + " is  deleted Successfully";
		}
		return "Project With Id: " + projectId + " is Not Present";
	}

	@Override
	public List<ProjectEngagement> SearchByEngagedEmployee(String empName) {
		List<ProjectEngagement> emplist = projectEngagementRepo.SearchByEngagedEmployee(empName);
		return emplist;
	}

	@Override
	public List<ProjectEngagement> SearchByProjectName(String projectName) {
		List<ProjectEngagement> projectList = projectEngagementRepo.SearchByProjectName(projectName);
		return projectList;
	}

	@Override
	public List<ProjectEngagement> SearchProjectsByDate(String startDate, String endDate) {
		return projectEngagementRepo.findByProjectDate(startDate, endDate);
	}
	
}
