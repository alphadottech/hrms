package com.adt.hrms.controller;

import com.adt.hrms.model.InterviewCandidateDetails;
import com.adt.hrms.service.InterviewCandidateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/interviewCandidate")
public class InterviewCandidateController {

    private static final Logger LOGGER = LogManager.getLogger(InterviewCandidateController.class);

    @Autowired
    InterviewCandidateService interviewCandidateService;

    @PostMapping("saveInterviewCandidate")
    public ResponseEntity<String> saveInterviewCandidate(@RequestBody InterviewCandidateDetails interviewCandidateDetails, HttpServletRequest request) {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<>(interviewCandidateService.saveInterviewCandidateDetail(interviewCandidateDetails), HttpStatus.OK);
    }

    @GetMapping("/allInterviewCandidate")
    public ResponseEntity<List<InterviewCandidateDetails>> getAllInterviewCandidate(HttpServletRequest request){
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<List<InterviewCandidateDetails>>(interviewCandidateService.getAllInterviewCandidateDetail(), HttpStatus.OK);
    }

    @PutMapping("/updateInterviewCandidate/{candidateId}")
    public ResponseEntity<String> updateInterviewCandidate(@PathVariable("candidateId") int candidateId, @RequestBody InterviewCandidateDetails interviewCandidateDetails, HttpServletRequest request){
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return  new ResponseEntity<>(interviewCandidateService.updateInterviewCandidateDetail(candidateId, interviewCandidateDetails), HttpStatus.OK);
    }

    @GetMapping("/interviewCandidateById/{candidateId}")
    public ResponseEntity<InterviewCandidateDetails> getInterviewCandidateById(@PathVariable("candidateId") int candidateId, HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<InterviewCandidateDetails>(interviewCandidateService.getInterviewCandidateById(candidateId), HttpStatus.OK);
    }

    @DeleteMapping("/interviewCandidateById/{candidateId}")
    public ResponseEntity<String> deleteInterviewCandidateById(@PathVariable("candidateId") int candidateId, HttpServletRequest request) throws NoSuchFieldException {
        LOGGER.info("API Call From IP: " + request.getRemoteHost());
        return new ResponseEntity<String>(interviewCandidateService.deleteInterviewCandidateById(candidateId), HttpStatus.OK);
    }
}
