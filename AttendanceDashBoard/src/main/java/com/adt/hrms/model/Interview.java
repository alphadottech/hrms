package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Interview")
public class Interview {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "Tech_id")
	private Integer techId;
	
	@Column(name = "Marks")
	private Integer marks;
	
	@Column(name = "Communication")
	private Integer communication;
	
	@Column(name = "Enthusiasm")
	private Integer enthusiasm;
	
	@Column(name = "Notes")
	private String notes;
	
	@Column(name = "Offer_released")
	private boolean offerReleased;
	
	@Column(name = "WorkExInYears")
	private double workExInYears;
	
	@Column(name = "InterviewerName")
	private String interviewerName;
	
	@Column(name = "CandidateName")
	private String candidateName;
	
	@Column(name = "Source")
	private String source;
	
	@Column(name = "OfferAccepted")
	private boolean offerAccepted;

	@Column(name = "PositionId")
	private Integer positionId;

}
