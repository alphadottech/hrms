package com.adt.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.PositionRepo;
import com.adt.hrms.service.PositionService;
import com.adt.hrms.ui.PositionUIModel;
import com.adt.hrms.util.InMemoryMap;


@Service
public class PositionServiceImpl implements PositionService {
	
	@Autowired
	private PositionRepo positionRepo;

	@Override
	public String savePosition(PositionModel pm) {
		Optional<PositionModel> opt= positionRepo.findById(pm.getId());
		if(opt.isPresent())
			return "Position with Id "+pm.getId()+" is alredy avalable Pls Insert new ID....";
		return positionRepo.save(pm).getId()+"Position is Saved";
				
	}

	@Override
	public List<PositionModel> getAllPosition() {
		List<PositionModel> list = positionRepo.findAll();
		return list;
	}

	@Override
	public PositionModel getPosition(Integer id) {
		Optional<PositionModel> opt = positionRepo.findById(id);
		if(opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public String updatePosition(PositionModel pm) {
		
		return positionRepo.save(pm).getId()+"Position Updated Successfully";
	}

	@Override
	public List<PositionUIModel> getAllUIPosition() {
List<PositionModel> poslist = getAllPosition();
		
		List<PositionUIModel> uiposlist = new ArrayList<PositionUIModel>();
		
		for (int i = 0; i < poslist.size(); i++) {
		    PositionModel dpm = poslist.get(i);
			PositionUIModel obj = new PositionUIModel();
			obj.setId(dpm.getId());
			obj.setTechId(dpm.getTechId());
			obj.setTechDesc(InMemoryMap.avtechnologymap.get(dpm.getTechId()));
			obj.setExperienceInYear(dpm.getExperienceInYear());
			obj.setPositionCloseDate(dpm.getPositionCloseDate());
			obj.setPositionOpenDate(dpm.getPositionOpenDate());
			obj.setStatus(InMemoryMap.avstatusmap.get(dpm.getStatus()));
			obj.setPositionType(InMemoryMap.avpositiontypemap.get(dpm.getPositionType()));
			uiposlist.add(obj);
			
		} 
	
		return uiposlist;
	
		
	}

}
