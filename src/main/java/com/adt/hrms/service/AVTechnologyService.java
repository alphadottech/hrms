package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.AVTechnology;

public interface AVTechnologyService {

	public String saveTechnology(AVTechnology tc);
	public List<AVTechnology> getAllTechnology();
	public AVTechnology getTechnology(Integer techId);


}
