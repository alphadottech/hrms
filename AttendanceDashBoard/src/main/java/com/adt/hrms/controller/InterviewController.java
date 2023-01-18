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

import com.adt.hrms.model.Interview;
import com.adt.hrms.service.InterviewService;

@RestController
@RequestMapping("/Interview")
public class InterviewController {
	
	@Autowired
	private InterviewService interviewService;
	
	
	@PostMapping("/saveInterview")
	public ResponseEntity<String> saveInterviewPosition(@RequestBody Interview in) {
		
		return new ResponseEntity<>(interviewService.saveEmpIP(in), HttpStatus.OK);
	}
	
	
	@GetMapping("/getAllEmpIP")
	public ResponseEntity<List<Interview>> getAllInterviewPosition() {
			return new ResponseEntity<>(interviewService.getAllEmpIP(), HttpStatus.OK);
	}

	@PutMapping("/updateEmpIP")
	public ResponseEntity<String> updateInterviewPosition(@RequestBody Interview in) {
		return new ResponseEntity<>(interviewService.updateEmpIP(in), HttpStatus.OK);
	}

	
	@GetMapping("/findByEmpIPId/{empId}")
	public ResponseEntity<Interview> findEmployeeIPByEmployeeId(@PathVariable("empId") Integer empId ){
		
		return new ResponseEntity<Interview>(interviewService.getEmployeeByEmpIP(empId), HttpStatus.OK);
		
	}
}
