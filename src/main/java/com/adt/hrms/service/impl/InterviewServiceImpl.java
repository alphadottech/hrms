package com.adt.hrms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Interview;
import com.adt.hrms.repository.InterviewRepository;
import com.adt.hrms.service.InterviewService;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;

	@Override
	public String saveInterview(Interview in) {
//		Optional<Interview> opt=interviewRepository.findById(in.getInterviewId());
//		if(opt.isPresent())
//           return "Interview Id "+in.getInterviewId()+" is already avalable Pls Insert new ID....";
		interviewRepository.save(in);
		return " New interview ID is Saved";
			
		
	}

	@Override
	public List<Interview> listAllInterviewDetails() {
		List<Interview> list=interviewRepository.findAll();
		return list;
	}
	
	@Override
	public String updateToInterviewDetails(Interview emp) {		
		return interviewRepository.save(emp).getInterviewId()+" Details Updated Successfully!";
	}
	
	@Override
	public Interview getInterviewDetailsById(Integer empId) {
		
		Optional<Interview> opt=interviewRepository.findById(empId);
		if (opt.isPresent()) 
			return opt.get();
		else
			return null;	
	}
	
	//HRMS-56 START New method added
	@Override
	public Interview getInterviewDetailByInterviewIdAndRound(Integer interviewId, Integer round) {
		// TODO Auto-generated method stub
		
		Optional<Interview> optional = interviewRepository.getInterviewDetailByInterviewIdAndRound(interviewId, round);
		
		if(!optional.isPresent())
			return null;
		
		return optional.get();

	}
	//HRMS-56 END
	

	
}

