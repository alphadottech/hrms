package com.adt.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="InterviewRounds")
public class InterviewRounds {
	
	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="interviewId")
	private Integer interviewId;
	
	@Column(name="interviewName")
	private String interviewerName;
	
	@Column(name="notes")
	private String notes;
	
	@Column(name="passable")
	private boolean passable; 

}
