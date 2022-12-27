package com.adt.hrms.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.service.AttendanceService;
import com.adt.hrms.service.AttendanceServiceImpl;

@RestController
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	

	
	   @PutMapping("/punching/{id}/{status}")
	    public ResponseEntity<String> status(@PathVariable int id, @PathVariable String status) throws ParseException {

	        return ResponseEntity.ok(attendanceService.setStatus(id, status));
	    }

}
