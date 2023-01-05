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

import com.adt.hrms.model.CandidatePipeline;
import com.adt.hrms.model.InterviewRounds;
import com.adt.hrms.service.CandidatePipelineService;
import com.adt.hrms.service.InterviewRoundService;

@RestController
@RequestMapping("/candidatePipeline")
public class CandidatePipelineController {
	
	@Autowired
	private CandidatePipelineService candidatePipelineService;
	
	@Autowired
	private InterviewRoundService interviewRoundService;
	
	@PostMapping("saveCandidate")
	public ResponseEntity<String> saveCandidatePipeline(@RequestBody CandidatePipeline cp){
		return new ResponseEntity<>(candidatePipelineService.saveCandidatePipelineDetails(cp), HttpStatus.OK);
	}
	
	@GetMapping("/getCandidatePipelineDetails")
	public ResponseEntity<List<CandidatePipeline>> getAllCandidatePipeline(){
		return new ResponseEntity<>(candidatePipelineService.getAllCandidatePipeline(), HttpStatus.OK);
		
	}
	
	@PutMapping("/updateCandidatePipelineDetails")
	public ResponseEntity<String> updateCandidatePipelineDetails(@RequestBody CandidatePipeline cp){
		return new ResponseEntity<>(candidatePipelineService.updateCandidatePipelineDetails(cp), HttpStatus.OK);
	}
	
	@GetMapping("/getCandidatePipelineById/{id}")
	public ResponseEntity<CandidatePipeline> getCandidatePipelineDetailsById(@PathVariable("id") Integer id){
		return new ResponseEntity<>(candidatePipelineService.getCandidatePipelineById(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCandidatePipelineById/{id}")
	public ResponseEntity<String> deleteCandidatePipelineById(@PathVariable("id") Integer id){
		return new ResponseEntity<>(candidatePipelineService.deleteCandidateById(id), HttpStatus.OK);
	}
	
	@PostMapping("/saveInterviewRounds")
	public ResponseEntity<String> saveInterviewRouds(@RequestBody InterviewRounds ir){
		return new ResponseEntity<>(interviewRoundService.saveInterviewRounds(ir), HttpStatus.OK);
	}
	
	@GetMapping("/getAllInterviewRounds")
	public ResponseEntity<List<InterviewRounds>> getAllInterviewRounds(){
		return new ResponseEntity<List<InterviewRounds>>(interviewRoundService.getAllInterviewRounds(), HttpStatus.OK);
	}
	
	@PutMapping("/updateInterviewRounds")
	public ResponseEntity<String> updateInterviewRounds(@RequestBody InterviewRounds ir){
		return new ResponseEntity<>(interviewRoundService.updateInterviewRounds(ir), HttpStatus.OK);
	}
	
	@GetMapping("/getInterviewRoundsById/{id}")
	public ResponseEntity<InterviewRounds> getInterviewRoundsById(@PathVariable("id") Integer id){
		return new ResponseEntity<>(interviewRoundService.getInterviewRoundsById(id), HttpStatus.OK);
		
	}
}


