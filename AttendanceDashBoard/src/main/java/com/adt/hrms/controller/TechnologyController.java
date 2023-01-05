package com.adt.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.adt.hrms.model.Technology;
import com.adt.hrms.service.TechnologyService;

@RestController
@RequestMapping("/technology")
public class TechnologyController {
	
	@Autowired
	private TechnologyService technologyService;
	
	
	@PostMapping("/savetech")
	public ResponseEntity<String> saveTechnology(@RequestBody Technology tc)
	{
		return new ResponseEntity<>(technologyService.saveTechnology(tc), HttpStatus.OK);
	}
	
	@GetMapping("/alltech")
	public ResponseEntity<List<Technology>> getAllTechnologies()
	{
		return new ResponseEntity<List<Technology>>(technologyService.getAllTechnology(), HttpStatus.OK);
	}
	
	@PutMapping("/updatetech")
	public ResponseEntity<String> updateTechnology(@RequestBody Technology tc){
		return new ResponseEntity<>(technologyService.updateTechnology(tc), HttpStatus.OK);
	}
	
	@GetMapping("/getTechById/{techId}")
	public ResponseEntity<Technology> getTechnologyById(@PathVariable("techId") Integer techId)
	{
		return new ResponseEntity<>(technologyService.getTechnology(techId), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteBytechId/{techId}")
	public ResponseEntity<String> deleteByTechId(@PathVariable("techId") Integer techId)
	{
		return new ResponseEntity<String>(technologyService.deleteTechnologyById(techId), HttpStatus.OK);
	}
	
	
	
}
