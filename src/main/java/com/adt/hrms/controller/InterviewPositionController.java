package com.adt.hrms.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.adt.hrms.model.Interview;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.service.InterviewService;
import com.adt.hrms.service.PositionService;
import com.adt.hrms.ui.PositionDateConverter;
import com.adt.hrms.ui.PositionUIModel;

@RestController
@RequestMapping("/intervPosition")
public class InterviewPositionController {
	
	private static final Logger LOGGER = LogManager.getLogger(EmployeeOperationController.class);
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private InterviewService interviewService;
	

	@PostMapping("/addPosition")
	public ResponseEntity<String> savePosition(@RequestBody PositionModel pm) {
		LOGGER.info("Employeeservice:InterviewPosition:savePosition info level log message");
		return new ResponseEntity<>(positionService.savePosition(pm), HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllPosition")
	public ResponseEntity <List<PositionUIModel>> getAllPosition() {
		LOGGER.info("Employeeservice:InterviewPosition:getAllPosition info level log message");
		return new ResponseEntity<>(positionService.getAllUIPosition(), HttpStatus.OK);
		
	}
	
	@GetMapping("/getByPositionId/{id}")
	public ResponseEntity<PositionModel> getByPositionId(@PathVariable("id") Integer id){
		LOGGER.info("Employeeservice:InterviewPosition:getByPositionId info level log message");
		return new ResponseEntity<PositionModel>(positionService.getPosition(id), HttpStatus.OK);
		
	}
	
	@PutMapping("/updatePosition")
	public ResponseEntity<String> updatePosition(@RequestBody PositionModel pm){
		LOGGER.info("Employeeservice:InterviewPosition:updatePosition info level log message");
		return new ResponseEntity<>(positionService.updatePosition(pm), HttpStatus.OK);
	}
	
	@PostMapping("/saveInterview")
	public ResponseEntity<String> saveInterviewPosition(@RequestBody Interview in) {
		LOGGER.info("Employeeservice:InterviewPosition:saveInterviewPosition info level log message");
		return new ResponseEntity<>(interviewService.saveEmpIP(in), HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllEmpIP")
	public ResponseEntity<List<Interview>> getAllInterviewPosition() {
		LOGGER.info("Employeeservice:InterviewPosition:getAllInterviewPosition info level log message");
			return new ResponseEntity<>(interviewService.getAllEmpIP(), HttpStatus.OK);
	}

	@PutMapping("/updateEmpIP")
	public ResponseEntity<String> updateInterviewPosition(@RequestBody Interview in) {
		LOGGER.info("Employeeservice:InterviewPosition:updateInterviewPosition info level log message");
		return new ResponseEntity<>(interviewService.updateEmpIP(in), HttpStatus.OK);
	}

	
	@GetMapping("/findByEmpIPId/{empId}")
	public ResponseEntity<Interview> findEmployeeIPByEmployeeId(@PathVariable("empId") Integer empId ){
		LOGGER.info("Employeeservice:InterviewPosition:findEmployeeIPByEmployeeId info level log message");
		return new ResponseEntity<Interview>(interviewService.getEmployeeByEmpIP(empId), HttpStatus.OK);
		
	}
	
	@PostMapping("/savePosition")
	public ResponseEntity<String> savePositionNew(@RequestBody PositionDateConverter pdc) {
		LOGGER.info("Employeeservice:InterviewPosition:savePositionNew info level log message");
		return new ResponseEntity<>(positionService.savePositionNew(pdc), HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllPositionNew")
	public ResponseEntity <List<PositionDateConverter>> getAllPositionNew() {
		LOGGER.info("Employeeservice:InterviewPosition:getAllPositionNew info level log message");
		return new ResponseEntity<>(positionService.getAllPositionNew(), HttpStatus.OK);
		
	}
	
	
	
	

	
	
	
}
