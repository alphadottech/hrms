package com.adt.hrms.ui;

import java.util.List;

import jakarta.persistence.Id;

public class PositionDateConverter {

	@Id
	private Integer positionId;

	private String positionName;

	private List<String> techStack;

	private String positionOpenDate;

	private String positionCloseDate;

	private String status;

	private String  experienceInYear;

	private boolean remote;

	private String positionType;

	private Integer vacancy;

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public List<String> getTechStack() {
		return techStack;
	}

	public void setTechStack(List<String> techStack) {
		this.techStack = techStack;
	}

	public String getPositionOpenDate() {
		return positionOpenDate;
	}

	public void setPositionOpenDate(String positionOpenDate) {
		this.positionOpenDate = positionOpenDate;
	}

	public String getPositionCloseDate() {
		return positionCloseDate;
	}

	public void setPositionCloseDate(String positionCloseDate) {
		this.positionCloseDate = positionCloseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExperienceInYear() {
		return experienceInYear;
	}

	public void setExperienceInYear(String experienceInYear) {
		this.experienceInYear = experienceInYear;
	}

	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

	public Integer getVacancy() {
		return vacancy;
	}

	public void setVacancy(Integer vacancy) {
		this.vacancy = vacancy;
	}

	public PositionDateConverter(Integer positionId, String positionName, List<String> techStack, String positionOpenDate,
			String positionCloseDate, String status, String experienceInYear, boolean remote, String positionType,
			Integer vacancy) {
		super();
		this.positionId = positionId;
		this.positionName = positionName;
		this.techStack = techStack;
		this.positionOpenDate = positionOpenDate;
		this.positionCloseDate = positionCloseDate;
		this.status = status;
		this.experienceInYear = experienceInYear;
		this.remote = remote;
		this.positionType = positionType;
		this.vacancy = vacancy;
	}

	@Override
	public String toString() {
		return "PositionDateConverter [positionId=" + positionId + ", positionName=" + positionName + ", techStack=" + techStack
				+ ", positionOpenDate=" + positionOpenDate + ", positionclosedate=" + positionCloseDate + ", status="
				+ status + ", experienceInYear=" + experienceInYear + ", remote=" + remote + ", positionType="
				+ positionType + ", vacancy=" + vacancy + "]";
	}

	public PositionDateConverter() {
		super();
	}
}