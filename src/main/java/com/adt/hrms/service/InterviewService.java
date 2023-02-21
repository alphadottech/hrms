package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.InboundInterview;
import com.adt.hrms.model.Interview;
import com.adt.hrms.model.OutboundInterview;

public interface InterviewService {
	
	public String saveEmpIP(Interview in);

	public	List<Interview> getAllEmpIP();

	public String updateEmpIP(Interview emp);

	public	Interview getEmployeeByEmpIP(Integer empId);
	
	public String saveInboundInterview(InboundInterview inboundInterview);

    public String saveOutboundInterview(OutboundInterview outboundInterviewRequest);
	

}
