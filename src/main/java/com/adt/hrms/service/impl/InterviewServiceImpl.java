package com.adt.hrms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.InboundInterview;
import com.adt.hrms.model.Interview;
import com.adt.hrms.model.OutboundInterview;
import com.adt.hrms.repository.InboundInterviewRepository;
import com.adt.hrms.repository.InterviewRepository;
import com.adt.hrms.repository.OutboundInterviewRepository;
import com.adt.hrms.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;
	
	@Autowired
	private InboundInterviewRepository inboundInterviewRepository;
	
	@Autowired
	private OutboundInterviewRepository outboundInterviewRepository;
	
	@Override
	public String saveEmpIP(Interview in) {
		Optional<Interview> opt=interviewRepository.findById(in.getId());
		if(opt.isPresent())
           return "Interview Id "+in.getId()+" is already avalable Pls Insert new ID....";
		return interviewRepository.save(in).getId()+" New interview ID is Saved";
			
		
	}

	@Override
	public List<Interview> getAllEmpIP() {
		List<Interview> list=interviewRepository.findAll();
		return list;
	}
	
	@Override
	public String updateEmpIP(Interview emp) {		
		return interviewRepository.save(emp).getId()+" Details Updated Successfully!";
	}
	
	@Override
	public Interview getEmployeeByEmpIP(Integer empId) {
		
		Optional<Interview> opt=interviewRepository.findById(empId);
		if (opt.isPresent()) 
			return opt.get();
		else
			return null;	
	}
	
	@Override
	public String saveInboundInterview(InboundInterview inboundInterviewRequest) {
		
		inboundInterviewRepository.save(inboundInterviewRequest);
		 
		 return "saved successfully";
	}

	@Override
	public String saveOutboundInterview(OutboundInterview outboundInterview) {
		outboundInterviewRepository.save(outboundInterview);
		 return "saved successfully";
	}
	
}

