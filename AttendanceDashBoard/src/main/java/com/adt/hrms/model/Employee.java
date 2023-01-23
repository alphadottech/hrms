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
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "EmployeeDetails")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EmpId")
	private Integer empId;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "MobileNo")
	private Long mobileNo;
	
	@Column(name = "EmailId")
	private String emailId;
	
	@Column(name = "designation")
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
