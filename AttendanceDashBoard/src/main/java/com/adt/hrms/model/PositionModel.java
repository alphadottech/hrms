package com.adt.hrms.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name="Position")
public class PositionModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "tech_id")
	private Integer techId;
	
	@Column(name = "position_open_date")
	private LocalDateTime positionOpenDate;
	
	@Column(name = "position_close_date")
	private LocalDateTime positionCloseDate;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "Experience_in_year")
	private double experienceInYear;
	
	@Column(name = "remote")
	private boolean remote;
	
	@Column(name = "position_type")
	private String positionType;
	
	
}
