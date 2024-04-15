package com.adt.hrms.request;

import java.time.Instant;

import javax.persistence.Column;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public class EmployeeRequest {
	private int employeeId;

	private String dob;

	private String email;

	private String firstName;

	private String gender;

	private Boolean isEmailVerified;

	private String lastName;

	private String maritalStatus;

	private Long mobileNo;

	private String userName;


}
