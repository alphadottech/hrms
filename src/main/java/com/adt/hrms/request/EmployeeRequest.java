package com.adt.hrms.request;

import java.time.Instant;

import javax.persistence.Column;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
public class EmployeeRequest {
	private int employeeId;

	private Boolean isActive;

	private String designation;

	private String dob;

	private String email;

	private String firstName;

	private String gender;

	private Boolean isEmailVerified;

	private String joinDate;

	private String lastName;

	private String maritalStatus;

	private Long mobileNo;

	private Double salary;

	private String userName;

	private String bankName;

	private String accountNumber;

	private String ifscCode;

}
