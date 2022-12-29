package com.adt.hrms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.PositionRepo;

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

}
