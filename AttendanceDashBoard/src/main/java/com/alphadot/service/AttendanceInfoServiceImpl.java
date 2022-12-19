package com.alphadot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alphadot.repository.AttendanceInfoRepository;
@Service
public class AttendanceInfoServiceImpl implements AttendanceInfoService {
	@Autowired
	private AttendanceInfoRepository attendanceInfoRepository;
}
