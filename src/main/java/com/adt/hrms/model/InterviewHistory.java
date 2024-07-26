package com.adt.hrms.model;

import java.time.LocalDate;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "interview_history")
public class InterviewHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interview_history_seq")
	@SequenceGenerator(name = "interview_history_seq", allocationSize = 1, schema = "employee_schema")
	@Column(name = "history_id")
	private Integer historyId;
	@Column(name = "interview_id")
	private Integer interviewId;
	@Column(name = "rounds")
	private Integer rounds;
	@Column(name = "candidate_name")
	private String candidateName;
	@Column(name = "client_name")
	private String clientName;
	@Column(name = "communication")
	private Integer communication;
	@Column(name = "interview_date")
	private LocalDate date;
	@Column(name = "enthusiasm")
	private Integer enthusiasm;
	@Column(name = "interviewer_name")
	private String interviewerName;
	@Column(name = "marks")
	private Integer marks;
	@Column(name = "notes")
	private String notes;
	@Column(name = "offer_accepted")
	private Boolean offerAccepted;
	@Column(name = "offer_released")
	private Boolean offerReleased;
	@Column(name = "source")
	private String source;
	@Column(name = "status")
	private String status;
	@Column(name = "type")
	private String type;
	@Column(name = "work_ex_in_years")
	private Double workexInYears;
	@Column(name = "candidate_id")
	private Integer candidateId;
	@Column(name = "position_id")
	private Integer positionId;
	@Column(name = "tech_id")
	private Integer techId;

}
