package com.adt.hrms.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(catalog = "EmployeeDB", schema = "employee_schema", name = "holiday")
@Data
@NoArgsConstructor
@ToString
public class Holiday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int hId;
	
	@Column(name = "holiday_name")
	private String holidayName;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "month")
	private String month; 
	
	@Column(name = "day")
	private String day;
}

