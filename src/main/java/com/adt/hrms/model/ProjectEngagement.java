package com.adt.hrms.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "project_engagement")
public class ProjectEngagement {


	@Id
	@Column(name = "project_id")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String projectId = UUID.randomUUID().toString();

	@Column(name = "end_date")
	private String endDate;

   @Column(name = "primary_resource")
   private String primaryResource;

	@Column(name="secondary_resource")
	private String secondaryResource;

	@Column(name = "end_client")
	private String endClient;

	@Column(name = "contractor")
	private String contractor;

	@Column(name = "start_date")
	private String startDate;

	@Column(name = "status")
	private boolean status;

}
