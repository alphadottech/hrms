package com.adt.hrms.service;

import java.io.IOException;
import java.util.List;

import com.adt.hrms.model.Interview;
import com.adt.hrms.ui.InterviewModelDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface InterviewService {

	public String saveInterview(Interview in);

	public List<Interview> listAllInterviewDetails();

	public String updateToInterviewDetails(Interview emp);

	public Interview getInterviewDetailsById(Integer empId);

	public Interview getInterviewDetailByInterviewIdAndRound(Integer interviewId, Integer round);

	public Interview updateInterviewDetailByInterviewIdAndRound(Integer interviewId, Integer round, InterviewModelDTO intw);

	public String saveInterviewNew(InterviewModelDTO intwDto);

	public List<Interview> SearchByCandidateName(String candidateName);

	public List<Interview> SearchBySource(String source);

	public List<Interview> SearchByClientName(String clientName);

	public void listAllInterviewDetailsInExcel(HttpServletResponse responseExcel) throws IOException;
	
	public void listAllPositionDetailsInExcel(HttpServletResponse responseExcel) throws IOException;

}
