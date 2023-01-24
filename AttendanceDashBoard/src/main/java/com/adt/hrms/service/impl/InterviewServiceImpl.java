package com.adt.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Interview;
import com.adt.hrms.repository.InterviewRepository;
import com.adt.hrms.service.InterviewService;
import com.adt.hrms.ui.InterviewUIModel;
import com.adt.hrms.util.InMemoryMap;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;

	@Override
	public String saveEmpIP(Interview in) {
		// System.out.println("hu");
		Optional<Interview> opt = interviewRepository.findById(in.getId());
		if (opt.isPresent())

			return "Interview Id " + in.getId() + " is already avalable Pls Insert new ID....";
		return interviewRepository.save(in).getId() + " New interview ID is Saved";

	}

	@Override
	public List<Interview> getAllEmpIP() {
		List<Interview> list = interviewRepository.findAll();
		return list;
	}

	@Override
	public String updateEmpIP(Interview emp) {
		return interviewRepository.save(emp).getId() + " Details Updated Successfully!";
	}

	@Override
	public Interview getEmployeeByEmpIP(Integer empId) {

		Optional<Interview> opt = interviewRepository.findById(empId);
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public List<InterviewUIModel> getAllUIInterview() {
		List<Interview> interviewlist = getAllEmpIP();

		List<InterviewUIModel> interviewuilist = new ArrayList<InterviewUIModel>();

		for (int i = 0; i < interviewlist.size(); i++) {

			Interview interviewobj = interviewlist.get(i);

			InterviewUIModel interviewuiobj = new InterviewUIModel();

			interviewuiobj.setId(interviewobj.getId());
			interviewuiobj.setTechId(interviewobj.getTechId());
			interviewuiobj.setTechDesc(InMemoryMap.avtechnologymap.get(interviewobj.getTechId()));
			interviewuiobj.setMarks(interviewobj.getMarks());
			interviewuiobj.setCommunication(interviewobj.getCommunication());
			interviewuiobj.setEnthusiasm(interviewobj.getEnthusiasm());
			interviewuiobj.setNotes(interviewobj.getNotes());
			interviewuiobj.setOfferReleased(interviewobj.isOfferReleased());
			interviewuiobj.setWorkExInYears(interviewobj.getWorkExInYears());
			interviewuiobj.setInterviewerName(interviewobj.getInterviewerName());
			interviewuiobj.setCandidateName(interviewobj.getCandidateName());
			interviewuiobj.setSource(interviewobj.getSource());
			interviewuiobj.setOfferAccepted(interviewobj.isOfferAccepted());
			interviewuiobj.setPositionId(interviewobj.getPositionId());

			interviewuilist.add(interviewuiobj);

		}

		return interviewuilist;
	}
}
