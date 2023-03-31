package com.adt.hrms.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "interview")
public class Interview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "interview_id", columnDefinition = "serial")
	private Integer interviewId;

	@ManyToOne(targetEntity= AVTechnology.class,cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn( name = "tech_id", referencedColumnName = "tech_id")
    private AVTechnology avtechnology;

	@Column(name = "Marks")
	private Integer marks;

	@Column(name = "Communication")
	private Integer communication;

	@Column(name = "Enthusiasm")
	private Integer enthusiasm;

	
	@Column(name = "Notes")
	private String notes;

	@Column(name = "Offer_released")
	private Boolean offerReleased;

	@Column(name = "WorkExInYears")
	private Double workExInYears;

	@Column(name = "InterviewerName")
	private String interviewerName;

	@Column(name = "CandidateName")
	private String candidateName;

	@Column(name = "Source")
	private String source;

	@Column(name = "OfferAccepted")
	private Boolean offerAccepted;


	@ManyToOne
	@JoinColumn(name = "PositionId", referencedColumnName = "id")
    private PositionModel position;
	

	@Column(name = "Type")
	private String type;

	@Column(name = "interview_date")
	private LocalDate date;

	@Column(name = "screening_round")
	private Boolean screeningRound;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "Rounds")
	private Integer rounds;

	@Column(name = "selected")
	private Boolean selected;

	@ManyToOne(targetEntity= InterviewCandidateDetails.class ,cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
	private InterviewCandidateDetails interviewCandidateDetails;

}
