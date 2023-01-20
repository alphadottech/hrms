package com.adt.hrms.controller;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.service.AttendanceService;


@RestController
@RequestMapping("/attendance")
public class AttendanceController {
	
	private static final Logger LOGGER = LogManager.getLogger(AttendanceController.class);

	@Autowired
	private AttendanceService attendanceService;

	
	   @PutMapping("/punching/{id}/{status}")
	    public ResponseEntity<String> status(@PathVariable int id, @PathVariable String status) throws ParseException {
		   LOGGER.info("Employeeservice:Attendance:setStatus info level log message");
		   return ResponseEntity.ok(attendanceService.setStatus(id, status));
		   
	    }

}

