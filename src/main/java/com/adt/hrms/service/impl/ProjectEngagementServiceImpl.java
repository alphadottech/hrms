package com.adt.hrms.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.adt.hrms.util.AssetUtility;
import com.adt.hrms.util.ProjectEngagementUtility;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	@Transactional
	@Override
	public String saveProjectEngagementDetail(ProjectEngagement projectEngagement) {
		if (!ProjectEngagementUtility.validateEmployee(projectEngagement.getPrimaryResource())) {
			throw new IllegalArgumentException("Invalid Primary Resource Details");
		}
		if (!ProjectEngagementUtility.validateEmployee(projectEngagement.getSecondaryResource())) {
			throw new IllegalArgumentException("Invalid Secondary Resource Details");
		}
		if (!ProjectEngagementUtility.validateEmployee(projectEngagement.getContractor())) {
			throw new IllegalArgumentException("Invalid Contractor Details");
		}
		if (!ProjectEngagementUtility.validateEmployee(projectEngagement.getEndClient())) {
			throw new IllegalArgumentException("Invalid Client Name");
		}

		if(!AssetUtility.isValidateDate(projectEngagement.getStartDate())){
			throw new IllegalArgumentException("Invalid Date");
		}

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

		projectDetails.get().setContractor(projectEngagement.getContractor());
		projectDetails.get().setEndClient(projectEngagement.getEndClient());
//		projectDetails.get().setEngagedEmployee(projectEngagement.getEngagedEmployee());
		projectDetails.get().setSecondaryResource(projectEngagement.getSecondaryResource());
		projectDetails.get().setPrimaryResource(projectEngagement.getPrimaryResource());
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
//	public List<ProjectEngagement> SearchByEngagedEmployee(String empName) {
//		List<ProjectEngagement> emplist = projectEngagementRepo.SearchByEngagedEmployee(empName);
//		return emplist;
//	}

    public List<ProjectEngagement> SearchByProjectName(String projectName) {
		List<ProjectEngagement> projectList = projectEngagementRepo.SearchByProjectName(projectName);
		return projectList;
	}

	@Override
	public List<ProjectEngagement> SearchProjectsByDate(String startDate, String endDate) {
		return projectEngagementRepo.findByProjectDate(startDate, endDate);
	}



	public Page<ProjectEngagement> searchProjectEngagementbyFields(String primaryResource, String secondaryResource, String startDate, String endDate, int page, int size) {
		if (primaryResource != null && !primaryResource.isEmpty()) {
			if (primaryResource.matches(".*\\d.*") || primaryResource.matches(".*[!@#$%^&*(){}\\[\\]:;<>,.?/~_+\\-=|\"\\\\].*")) {
				throw new IllegalArgumentException("Invalid Primary Resource format.");
			}
		}

		if (secondaryResource != null && !secondaryResource.isEmpty()) {
			if (secondaryResource.matches(".*\\d.*") || secondaryResource.matches(".*[!@#$%^&*(){}\\[\\]:;<>,.?/~_+\\-=|\"\\\\].*")) {
				throw new IllegalArgumentException("Invalid Secondary Resource format.");
			}
		}

		if (startDate != null && !startDate.isEmpty()) {
			if (!AssetUtility.isValidateDate(startDate)) {
				throw new IllegalArgumentException("Invalid Start Date format or value.");
			}
		}

		Pageable pageable = PageRequest.of(page, size);
		Specification<ProjectEngagement> spec = Specification.where(null);

		if (primaryResource != null && !primaryResource.isEmpty()) {
			spec = spec.or((root, query, cb) -> cb.like(cb.lower(root.get("primaryResource")), primaryResource.toLowerCase() + "%"));
		}
		if (secondaryResource != null && !secondaryResource.isEmpty()) {
			spec = spec.or((root, query, cb) -> cb.like(cb.lower(root.get("secondaryResource")), secondaryResource.toLowerCase() + "%"));
		}
		if (startDate != null && !startDate.isEmpty()) {
			spec = spec.or((root, query, cb) -> cb.equal(root.get("startDate"), startDate));
		}
		if (endDate != null && !endDate.isEmpty()) {
			spec = spec.or((root, query, cb) -> cb.equal(root.get("endDate"), endDate));
		}
		return projectEngagementRepo.findAll(spec,pageable);
	}


	public ByteArrayInputStream getExcelData() throws IOException {
		List<ProjectEngagement> list=projectEngagementRepo.findAll();
		ByteArrayInputStream byteArrayInputStream= Helper.dataToExcel(list);
		return byteArrayInputStream;
	}
	
}
