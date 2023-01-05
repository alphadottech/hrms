package com.adt.hrms.service;



import java.util.List;

import com.adt.hrms.model.Technology;

public interface TechnologyService {
	
	public String saveTechnology(Technology tc);
	public List<Technology> getAllTechnology();
	public Technology getTechnology(Integer techId);
	public String updateTechnology(Technology tc);
	public String deleteTechnologyById(Integer techId);
	
	

}
