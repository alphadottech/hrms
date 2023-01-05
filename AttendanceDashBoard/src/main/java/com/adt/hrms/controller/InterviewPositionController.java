package com.adt.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.PositionModel;
import com.adt.hrms.service.PositionService;

@RestController
@RequestMapping("/intervPosition")
public class InterviewPositionController {
	
	@Autowired
	private PositionService positionService;
	
	

	@PostMapping("/addPosition")
	public ResponseEntity<String> savePosition(@RequestBody PositionModel pm) {
		return new ResponseEntity<>(positionService.savePosition(pm), HttpStatus.OK);
		//return new ResponseEntity<>(employeeService.saveEmp(emp), HttpStatus.OK);
	}
	
	@GetMapping("/getAllPosition")
	public ResponseEntity <List<PositionModel>> getAllPosition() {
		return new ResponseEntity<>(positionService.getAllPosition(), HttpStatus.OK);
		
	}
	
	@GetMapping("/getByPositionId/{id}")
	public ResponseEntity<PositionModel> getByPositionId(@PathVariable("id") Integer id){
		return new ResponseEntity<PositionModel>(positionService.getPosition(id), HttpStatus.OK);
		
	}
	
	@PutMapping("/updatePosition")
	public ResponseEntity<String> updatePosition(@RequestBody PositionModel pm){
		return new ResponseEntity<>(positionService.updatePosition(pm), HttpStatus.OK);
	}
	
	
	
}
