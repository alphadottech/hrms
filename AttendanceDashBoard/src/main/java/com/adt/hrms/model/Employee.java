package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "EmployeeDetails")
public class Employee {
	
	@Id
	@Column(name = "EmpId")
	private Integer empId;
	@Column(name = "FirstName")
	private String fName;
	@Column(name = "LastName")
	private String lName;
	@Column(name = "MobileNo")
	private Long mobileNo;
	@Column(name = "EmailId")
	private String emailId;
	@Column(name = "Designamtion")
	private String designation;
	@Column(name = "JoiningDate")
	private String joinDate;
	@Column(name = "Gender")
	private String gender;
	@Column(name = "DOB")
	private String dob;
	@Column(name = "MaritalStatus")
	private String maritalStatus;
	

}
