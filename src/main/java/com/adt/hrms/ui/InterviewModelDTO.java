package com.adt.hrms.ui;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class InterviewModelDTO {

	// PK
	private Integer interviewId;

	// PK
	private Integer rounds;

	// FK
	private Integer candidate_id;

	// FK
	private Integer tech_id;

	// FK
	private Integer position_id;

	private Integer marks;

	private Integer communication;

	private Integer enthusiasm;

	private String notes;

	private Boolean offerReleased;

	private Double workExInYears;

	private String interviewerName;

	private String candidateName;

	private String source;

	private Boolean offerAccepted;

	private String type;

	private String date;

	private String clientName;

	//HRMS-102 - start
	private String status;
//	private Boolean screeningRound;
//	private Boolean selected;
	//HRMS-102 - end

}
