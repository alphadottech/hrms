package com.alphadot.dao;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.BSONTimestampCodec;

@Document(collection = "attendance")
public class EmployeeStatus {

	private Integer empid;
	private LocalDate date;
	private BSONTimestampCodec  checkin;
	private BSONTimestampCodec checkout;
	private String leave;
	private Double workinghours;
	
	public int getEmpid() {
		return empid;
	}
	public void setEmpid(int empid) {
		this.empid = empid;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BSONTimestampCodec getCheckin() {
		return checkin;
	}
	public void setCheckin(BSONTimestampCodec checkin) {
		this.checkin = checkin;
	}
	public BSONTimestampCodec getCheckout() {
		return checkout;
	}
	public void setCheckout(BSONTimestampCodec checkout) {
		this.checkout = checkout;
	}
	public String getLeave() {
		return leave;
	}
	public void setLeave(String leave) {
		this.leave = leave;
	}
	public Double getWorkinghours() {
		return workinghours;
	}
	public void setWorkinghours(Double workinghours) {
		this.workinghours = workinghours;
	}
	public void setEmpid(Integer empid) {
		this.empid = empid;
	}
	
	

}
