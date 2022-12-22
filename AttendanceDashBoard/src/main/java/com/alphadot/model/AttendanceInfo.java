package com.alphadot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
//@Document(collection = "attendanceinfo")
@Table(name = "AttendanceInfo")
public class AttendanceInfo {
	@Id
	@Column(name = "EmpId")
	private Integer empId;
	
	@Column(name = "CheckIn")
	private LocalDate checkIn;
	@Column(name="CheckInTime")
	private LocalDateTime checkInTime;
	@Column(name="CheckOutTime")
	private LocalDateTime checkOutTime;
	@Column(name="WorkingHrs")
	private Double workingHrs;
	@Column(name="Status")
	private String status;
	

}
