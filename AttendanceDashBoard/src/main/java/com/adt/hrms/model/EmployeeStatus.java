package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Employeestatus")
public class EmployeeStatus {
	
	@Id
	@Column(name = "EmpId")
	private Integer empId;
	@Column(name = "ManagerId")
	private Integer ManagerId;
	@Column(name = "ProjectId")
	private String ProjectId;
	@Column(name = "EmployeeStatus")
	private String EmpStatus;
	@Column(name = "EmploymentType")
	private String EmploymentType;
	
	

}
