package com.adt.hrms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.adt.hrms.model.Employee;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.PositionRepo;

public class PositionServiceImpl implements PositionService {
	
	@Autowired
	private PositionRepo positionRepo;

	@Override
	public String savePosition(PositionModel pm) {
		Optional<PositionModel> opt= positionRepo.findById(pm.getId());
		if(opt.isPresent())
		{
			
		}
		return null;
			
	}

}
