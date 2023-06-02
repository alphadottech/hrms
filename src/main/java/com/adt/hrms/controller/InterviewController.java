package com.adt.hrms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.Interview;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.service.InterviewService;
import com.adt.hrms.service.PositionService;
import com.adt.hrms.ui.PositionDateConverter;
import com.adt.hrms.ui.PositionUIModel;

@RestController
@RequestMapping("/interview")
public class InterviewController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PositionService positionService;

	@Autowired
	private InterviewService interviewService;

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PostMapping("/addPosition")
	public ResponseEntity<String> savePosition(@RequestBody PositionModel pm) {
		LOGGER.info("Employeeservice:InterviewPosition:savePosition info level log message");
		return new ResponseEntity<>(positionService.savePosition(pm), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getAllPosition")
	public ResponseEntity<List<PositionUIModel>> getAllPosition() {
		LOGGER.info("Employeeservice:InterviewPosition:getAllPosition info level log message");
		return new ResponseEntity<>(positionService.getAllUIPosition(), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getByPositionId/{id}")
	public ResponseEntity<PositionModel> getByPositionId(@PathVariable("id") Integer id) {
		LOGGER.info("Employeeservice:InterviewPosition:getByPositionId info level log message");
		return new ResponseEntity<PositionModel>(positionService.getPosition(id), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PutMapping("/updatePosition")
	public ResponseEntity<String> updatePosition(@RequestBody PositionModel pm) {
		LOGGER.info("Employeeservice:InterviewPosition:updatePosition info level log message");
		return new ResponseEntity<>(positionService.updatePosition(pm), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PostMapping("/saveInterviewDetails")
	public ResponseEntity<String> saveInterviewDetails(@RequestBody Interview interviewRequest) {
		LOGGER.info("Employeeservice:Interview:saveInterviewDetails info level log message");
		return new ResponseEntity<>(interviewService.saveInterview(interviewRequest), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getAllInterviewDetails")
	public ResponseEntity<List<Interview>> getAllInterviewDetails() {
		LOGGER.info("Employeeservice:InterviewDetails:getAllInterviewDetails info level log message");
		return new ResponseEntity<>(interviewService.listAllInterviewDetails(), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PutMapping("/updateInterviewDetails")
	public ResponseEntity<String> updateInterviewDetails(@RequestBody Interview interviewRequest) {
		LOGGER.info("Employeeservice:Interview:updateInterviewDetails info level log message");
		return new ResponseEntity<>(interviewService.updateToInterviewDetails(interviewRequest), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/findByDetailsByEmployeeId/{empId}")
	public ResponseEntity<Interview> findEmployeeIPByEmployeeId(@PathVariable("empId") Integer empId) {
		LOGGER.info("Employeeservice:Interview:findEmployeeIPByEmployeeId info level log message");
		return new ResponseEntity<Interview>(interviewService.getInterviewDetailsById(empId), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@PostMapping("/savePosition")
	public ResponseEntity<String> savePositionNew(@RequestBody PositionDateConverter pdc) {
		LOGGER.info("Employeeservice:InterviewPosition:savePositionNew info level log message");
		return new ResponseEntity<>(positionService.savePositionNew(pdc), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getAllPositionNew")
	public ResponseEntity<List<PositionDateConverter>> getAllPositionNew() {
		LOGGER.info("Employeeservice:InterviewPosition:getAllPositionNew info level log message");
		return new ResponseEntity<>(positionService.getAllPositionNew(), HttpStatus.OK);
	}

	@PreAuthorize("@auth.allow('ROLE_ADMIN')")
	@GetMapping("/getInterviewDetailByIdAndRound")
	public ResponseEntity<Interview> getInterviewDetailByID(@RequestParam("interviewId") int interviewId,
			@RequestParam("round") int round) {
		LOGGER.info("Employeeservice:InterviewController:getInterviewDetailByID info level log message");
		Interview interview = interviewService.getInterviewDetailByInterviewIdAndRound(interviewId, round);
		if (interview == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(interview);
	}

}
