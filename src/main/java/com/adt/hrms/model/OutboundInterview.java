package com.adt.hrms.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "OutboundInterview")
public class OutboundInterview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "outbound_id", columnDefinition = "serial")
	private int outboundId;
	
	@Column(name = "InterviewerName")
	private String interviewerName;
	
	@Column(name = "CandidateName")
	private String candidateName;
	
	@Column(name = "Date")
    private LocalDate date;
	
	@Column(name = "Client")
	private String client;
	
	@Column(name = "Screening_round")
	private boolean screeningRound;
	
	@Column(name = "Main_round")
	private String mainRound;


	
}
