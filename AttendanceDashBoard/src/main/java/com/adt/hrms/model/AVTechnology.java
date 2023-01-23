package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "av_schema", name = "Technology")
public class AVTechnology {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tech_id")
	private Integer techId;
	
	@Column(name="description")
	private String description;
	
}
