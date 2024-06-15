package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "project_engagement")
public class ProjectEngagement {

	@Id
	@Column(name = "project_id")
	private String projectId;

	@Column(name = "end_date")
	private String endDate;

	@Column(name = "engaged_employee")
	private String engagedEmployee;

	@Column(name = "end_client")
	private String endClient;

	@Column(name = "contractor")
	private String contractor;

	@Column(name = "start_date")
	private String startDate;

	@Column(name = "status")
	private boolean status;

}
