package com.adt.hrms.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "project_engagement")
public class ProjectEngagement {

	@Id
	@Column(name = "project_id")
	private String projectId;

	// JIRA no. :- HRMS-90 START---
	@Column(name = "end_date")
	private String endDate;

	@Column(name = "engaged_employee")
	private String engagedEmployee;
	// JIRA no. :- HRMS-90 END---

	@Column(name = "project_description")
	private String projectDescription;

	// JIRA no. :- HRMS-90 START---
	@Column(name = "project_name")
	private String projectName;

	@Column(name = "start_date")
	private String startDate;
	// JIRA no. :- HRMS-90 END---

	@Column(name = "status")
	private boolean status;

}
