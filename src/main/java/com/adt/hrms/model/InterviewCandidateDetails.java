package com.adt.hrms.model;

import java.time.LocalDate;

import javax.validation.constraints.Email;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "CandidateDetails")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewCandidateDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "candidate_id")
	private Integer candidateId;

	@Column(name = "candidate_name")
	private String candidateName;

	@Column(name = "email_id")
	@Email
	private String emailId;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "address")
	private String address;

	@Column(name = "highest_qualification")
	private String highestQualification;

	@Column(name = "work_experience")
	private String workExperience;

	@Column(name = "technical_stack")
	private String technicalStack;

	@Column(name = "cv_shortlisted")
	private Boolean cvShortlisted;

	@Column(name = "last_ctc")
	private Double lastCTC;

	@Column(name = "expected_ctc")
	private Double expectedCTC;

	@Column(name = "passing_year")
	private String passingYear;

	@Column(name = "notice_period")
	private Integer noticePeriod;

	@Column(name = "dob")
	private LocalDate dob;
}
