package com.adt.hrms.model;

import java.time.Instant;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Proxy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(catalog = "EmployeeDB", schema = "user_schema", name = "_EMPLOYEE")
@Proxy(lazy = false)
@Data
public class Employee {

	@Id
	@Column(name = "EMPLOYEE_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1, schema = "user_schema")
	private int employeeId;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;


	@Column(name = "DOB")
	private String dob;

	@NaturalId
	@Column(name = "EMAIL", unique = true)
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "Gender")
	private String gender;

	@Column(name = "IS_EMAIL_VERIFIED", nullable = false)
	private Boolean isEmailVerified;


	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "maritalStatus")
	private String maritalStatus;

	@Column(name = "MobileNo")
	private Long mobileNo;

	@Column(name = "username")
	private String userName;
	
	@Column(name = "ADT_ID",nullable = false,unique = true)
	private String adtId;

}
