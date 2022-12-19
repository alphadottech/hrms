package com.alphadot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alphadot.service.AttendanceInfoService;

@RestController
public class AttendanceOperationsController {
	
	@Autowired
	private AttendanceInfoService attendanceInfoService;
}
