package com.alphadot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "attendanceinfo")
public class AttendanceInfo {
	private Integer empId;
	private LocalDate checkIn;
	private LocalDateTime checkInTime;
	private LocalDateTime checkOutTime;
	private Double workingHrs;
	private String status;
	

}
