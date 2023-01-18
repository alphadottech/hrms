package com.adt.hrms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Interview;
import com.adt.hrms.repository.InterviewRepository;

@Service
public class InterviewServiceImpl implements InterviewService {

//	@Override
//	public String saveEmpIP(Interview in) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	@Autowired
	private InterviewRepository interviewRepository;
	
	@Override
	public String saveEmpIP(Interview in) {
		//System.out.println("hu");
		Optional<Interview> opt=interviewRepository.findById(in.getId());
		if(opt.isPresent())
//			return "Employee with Id "+in.getId()+" is alredy avalable Pls Insert new ID....";
//		else {
//		interviewPositionRepository.save(in);
//		return " Employee is Saved";
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
	
}

