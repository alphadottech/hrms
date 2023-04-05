package com.adt.hrms.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.PositionRepo;
import com.adt.hrms.service.PositionService;
import com.adt.hrms.ui.PositionDateConverter;
import com.adt.hrms.ui.PositionUIModel;
import com.adt.hrms.util.InMemoryMap;


@Service
public class PositionServiceImpl implements PositionService {
	
	@Autowired
	private PositionRepo positionRepo;
	
	PositionModel pm = new PositionModel();

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
			
			LocalDate date1 = dpm.getPositionOpenDate().toLocalDate();
			String positionopenstr = date1.toString();
			obj.setPositionOpenDate(positionopenstr);;
			
			LocalDate date2 = dpm.getPositionCloseDate().toLocalDate();
			String positionclosestr = date2.toString();
			obj.setPositionCloseDate(positionclosestr);
			
			obj.setStatus(InMemoryMap.avstatusmap.get(dpm.getStatus()));
			obj.setPositionType(InMemoryMap.avpositiontypemap.get(dpm.getPositionType()));
			uiposlist.add(obj);
			
		} 
	
		return uiposlist;
	
		
	}
	
	

	@Override
	public String savePositionNew(PositionDateConverter pdc) {
		pm.setId(pdc.getUiid());
		Optional<PositionModel> opt =  positionRepo.findById(pm.getId());
		if(opt.isPresent())
			return "Position with Id "+pm.getId()+" is alredy avalable Pls Insert new ID....";
		else
			pm.setId(pdc.getUiid());
			pm.setTechId(pdc.getTechid());
			LocalTime time = LocalTime.now();
			String str = pdc.getPositionclosedate()+" "+String.valueOf(time);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			LocalDateTime posititonCloseDate = LocalDateTime.parse(str, formatter);
			pm.setPositionCloseDate(posititonCloseDate);
			
			LocalTime time1 = LocalTime.now();
			String str1 = pdc.getPositionopendate()+" "+String.valueOf(time1);
			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
			LocalDateTime positionOpenDate = LocalDateTime.parse(str1, formatter1);
			pm.setPositionOpenDate(positionOpenDate);
			
			pm.setExperienceInYear(pdc.getExperienceInYear());
			pm.setStatus(pdc.getStatus());
			pm.setRemote(pdc.isRemote());
			pm.setPositionType(pdc.getPositionType());
			
		
		return positionRepo.save(pm).getId()+ "Position is Saved";
	}

	@Override
	public List<PositionDateConverter> getAllPositionNew() {
		
		List<PositionModel> pmlist = getAllPosition();
		List<PositionDateConverter> pdclist = new ArrayList<PositionDateConverter>();
		
		for (int i = 0; i < pmlist.size(); i++) {
			PositionModel pm = pmlist.get(i);
			PositionDateConverter obj = new PositionDateConverter();
			obj.setUiid(pm.getId());
			obj.setTechid(pm.getTechId());
			obj.setStatus(pm.getStatus());
			obj.setExperienceInYear(pm.getExperienceInYear());
			obj.setRemote(pm.isRemote());
			obj.setPositionType(pm.getPositionType());
			
			LocalDate date1 = pm.getPositionOpenDate().toLocalDate();
			String positionopenstr = date1.toString();
			obj.setPositionopendate(positionopenstr);
			
			LocalDate date2 = pm.getPositionCloseDate().toLocalDate();
			String positionclosestr = date2.toString();
			obj.setPositionclosedate(positionclosestr);

			pdclist.add(obj);
			
			
		}return pdclist;
	}
	
	
	

}
