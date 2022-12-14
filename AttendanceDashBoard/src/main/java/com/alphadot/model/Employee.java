package com.alphadot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Document(collection = "employee")
@Data
public class Employee {
	
	@Id
	private Integer empId;
	private String name;
    private String emailId;
    private String designation;
    private Long mobileNo;
}
