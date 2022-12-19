package com.alphadot.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "employee")
public class Employee {
	
	@Id
	private Integer empId;
	private String name;
	private Long mobileNo;
	private String designation;
	private LocalDate joinDate;
	

}
