package com.adt.hrms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(catalog = "EmployeeDB", schema = "av_schema", name = "Technology")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AVTechnology {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "technology_seq")
	@SequenceGenerator(name = "technology_seq", allocationSize = 1, schema = "av_schema")
	@Column(name = "tech_id")
	private Integer techId;

	@Column(name = "description")
	private String description;

}
