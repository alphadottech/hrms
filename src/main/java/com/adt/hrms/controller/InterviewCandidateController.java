package com.adt.hrms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.InterviewCandidateDetails;
import com.adt.hrms.service.InterviewCandidateService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/interviewCandidate")
public class InterviewCandidateController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InterviewCandidateService interviewCandidateService;

	@PreAuthorize("@auth.allow('SAVE_INTERVIEW_CANDIDATE_DETAILS')")
	@PostMapping("saveInterviewCandidate")
	public ResponseEntity<String> saveInterviewCandidate(
			@RequestBody InterviewCandidateDetails interviewCandidateDetails, HttpServletRequest request) {
		LOGGER.info("API Call From IP: " + request.getRemoteHost());
		return new ResponseEntity<>(interviewCandidateService.saveInterviewCandidateDetail(interviewCandidateDetails),
				HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('GET_ALL_INTERVIEW_CANDIDATE_DETAILS')")
	@GetMapping("/allInterviewCandidate")
	public ResponseEntity<List<InterviewCandidateDetails>> getAllInterviewCandidate(HttpServletRequest request) {
		LOGGER.info("API Call From IP: " + request.getRemoteHost());
		return new ResponseEntity<List<InterviewCandidateDetails>>(
				interviewCandidateService.getAllInterviewCandidateDetail(), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('UPDATE_INTERVIEW_CANDIDATE_DETAILS_BY_ID')")
	@PutMapping("/updateInterviewCandidate/{candidateId}")
	public ResponseEntity<String> updateInterviewCandidate(@PathVariable("candidateId") int candidateId,
			@RequestBody InterviewCandidateDetails interviewCandidateDetails, HttpServletRequest request) {
		LOGGER.info("API Call From IP: " + request.getRemoteHost());
		return new ResponseEntity<>(
				interviewCandidateService.updateInterviewCandidateDetail(candidateId, interviewCandidateDetails),
				HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('GET_INTERVIEW_CANDIDATE_DETAILS_BY_ID')")
	@GetMapping("/interviewCandidateById/{candidateId}")
	public ResponseEntity<InterviewCandidateDetails> getInterviewCandidateById(
			@PathVariable("candidateId") int candidateId, HttpServletRequest request) throws NoSuchFieldException {
		LOGGER.info("API Call From IP: " + request.getRemoteHost());
		return new ResponseEntity<InterviewCandidateDetails>(
				interviewCandidateService.getInterviewCandidateById(candidateId), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('DELETE_INTERVIEW_CANDIDATE_BY_ID')")
	@DeleteMapping("/interviewCandidateById/{candidateId}")
	public ResponseEntity<String> deleteInterviewCandidateById(@PathVariable("candidateId") int candidateId,
			HttpServletRequest request) throws NoSuchFieldException {
		LOGGER.info("API Call From IP: " + request.getRemoteHost());
		return new ResponseEntity<String>(interviewCandidateService.deleteInterviewCandidateById(candidateId),
				HttpStatus.OK);
	}
}
