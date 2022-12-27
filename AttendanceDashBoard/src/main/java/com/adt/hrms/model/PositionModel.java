package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Position")
public class PositionModel {
	
	@Id
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "tech_id")
	private Integer TechId;
	
	@Column(name = "position_open_date")
	private String PositionOpenDate;
	
	@Column(name = "position_close_date")
	private String PositionCloseDate;
	
	@Column(name = "status")
	private String Status;
	
	@Column(name = "Experience_in_year")
	private String ExperienceInYear;
	
	@Column(name = "remote")
	private String Remote;
	
	@Column(name = "position_type")
	private String PositionType;
	
	
}
