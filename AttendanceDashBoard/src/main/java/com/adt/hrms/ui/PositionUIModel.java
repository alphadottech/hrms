package com.adt.hrms.ui;

import java.time.LocalDateTime;

public class PositionUIModel {
	
	
	private Integer id;
	
	
	private Integer techId;
	
	
	private String techDesc;
	
	
	private LocalDateTime positionOpenDate;
	
	
	private LocalDateTime positionCloseDate;
	
	
	private String status;
	
	
	private double experienceInYear;
	
	
	private boolean remote;
	
	
	private String positionType;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTechId() {
		return techId;
	}

	public void setTechId(Integer techId) {
		this.techId = techId;
	}
	
	
	public String getTechDesc() {
		return techDesc;
	}
	
	public void setTechDesc(String techDesc) {
		this.techDesc = techDesc;
	}

	public LocalDateTime getPositionOpenDate() {
		return positionOpenDate;
	}

	public void setPositionOpenDate(LocalDateTime positionOpenDate) {
		this.positionOpenDate = positionOpenDate;
	}

	public LocalDateTime getPositionCloseDate() {
		return positionCloseDate;
	}

	public void setPositionCloseDate(LocalDateTime positionCloseDate) {
		this.positionCloseDate = positionCloseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getExperienceInYear() {
		return experienceInYear;
	}

	public void setExperienceInYear(double experienceInYear) {
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

	
	
	
}
