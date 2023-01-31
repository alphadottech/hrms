package com.adt.hrms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "Interview")
public class Interview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
