package com.adt.hrms.service;

import java.util.List;

import com.adt.hrms.model.Holiday;

import jakarta.servlet.http.HttpServletResponse;

public interface HolidayService {
	
	public String saveHoliday(String holidayName, String date);
	
	public List<Holiday> getAllHolidays();
	
	public String downloadHolidayCalendar(HttpServletResponse resp);
	
	public String updateHolidayCalendar(Integer hId, String holidayName, String date);
	
	public String deleteHolidayFromCalendar(Integer hId);
	
	public Holiday getHolidayById(Integer hId);

}
