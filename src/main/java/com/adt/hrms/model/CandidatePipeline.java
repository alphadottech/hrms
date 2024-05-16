package com.adt.hrms.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "CandidatePipeline")
public class CandidatePipeline {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "interview_id")
	private Integer interviewId;

	@Column(name = "offer_release_date")
	private LocalDateTime offerReleaseDate;

	@Column(name = "last_communication_date")
	private LocalDateTime lastCommunicationDate;

	@Column(name = "target_onboarding_date")
	private LocalDateTime targetOnboardingDate;

}
