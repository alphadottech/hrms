package com.adt.hrms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.adt.hrms.model.Technology;
import com.adt.hrms.repository.TechnologyRepo;

@Service
public class TechnologyServiceImpl implements TechnologyService {
	
	@Autowired
	private TechnologyRepo technologyRepo;

	@Override
	public String saveTechnology(Technology tc) {
		
		Optional<Technology> opt = technologyRepo.findById(tc.getTechId());
		if(opt.isPresent())
			return "Technology with Id "+tc.getTechId()+" is alredy avalable Pls Insert new ID....";
		else
			return technologyRepo.save(tc).getTechId()+" Technology is Saved";
	}

	@Override
	public List<Technology> getAllTechnology() {
		List<Technology> tech = technologyRepo.findAll();
		return tech;
	}

	@Override
	public String updateTechnology(Technology tc) {
		
		return technologyRepo.save(tc).getTechId()+" Position updated Successfully";
	}

	@Override
	public Technology getTechnology(Integer tech_id) {
		Optional<Technology> opt = technologyRepo.findById(tech_id);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public String deleteTechnologyById(Integer techId) {
		Optional<Technology> opt = technologyRepo.findById(techId);
		if(opt.isPresent()) {
			technologyRepo.deleteById(techId);;
			return techId+ " has been Deleted";
		}
		else
			return "Invalid Employe Id :: "+techId;
	}
	
	
	

}
