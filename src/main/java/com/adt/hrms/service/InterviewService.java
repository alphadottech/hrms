package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.Interview;

public interface InterviewService {
	
	public String saveInterview(Interview in);

	public	List<Interview> listAllInterviewDetails();

	public String updateToInterviewDetails(Interview emp);

	public	Interview getInterviewDetailsById(Integer empId);
	
	//HRMS-56 START
	public Interview getInterviewDetailByInterviewIdAndRound(Integer interviewId, Integer round);
	//HRMS-56 END

}
