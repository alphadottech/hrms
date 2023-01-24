package com.adt.hrms.ui;

import lombok.Data;

@Data

public class InterviewUIModel {

	private Integer id;

	private Integer techId;

	private String techDesc;

	private Integer marks;

	private Integer communication;

	private Integer enthusiasm;

	private String notes;

	private boolean offerReleased;

	private double workExInYears;

	private String interviewerName;

	private String candidateName;

	private String source;

	private boolean offerAccepted;

	private Integer positionId;
}
