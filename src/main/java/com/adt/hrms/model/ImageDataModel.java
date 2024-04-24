package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(catalog = "EmployeeDB", schema = "payroll_schema", name = "image")
public class ImageDataModel {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "pic")
	private byte[] pic;

}
