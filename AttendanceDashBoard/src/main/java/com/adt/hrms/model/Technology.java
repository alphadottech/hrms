package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Technology")
public class Technology {
	
	
	@Id
	@Column(name="tech_id")
	private Integer techId;
	
	@Column(name="description")
	private String description;
	
}
