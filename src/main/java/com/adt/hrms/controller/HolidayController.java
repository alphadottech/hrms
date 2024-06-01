package com.adt.hrms.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.adt.hrms.model.Holiday;
import com.adt.hrms.service.HolidayService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HolidayService holidayService;

    @PreAuthorize("@auth.allow('SAVE_HOLIDAY_DATA')")
    @PostMapping("/saveHolidayDate")
    public ResponseEntity<String> addHolidayDate(@RequestParam("holidayName") String holidayName, @RequestParam("date") String date) {
        LOGGER.info("EmployeeService:HolidayController:addHolidayDate");
        String savedHolidayStatus = holidayService.saveHoliday(holidayName, date);

        if (savedHolidayStatus.toLowerCase().contains("holiday already present") || savedHolidayStatus.toLowerCase().contains("date already present"))
            return ResponseEntity.badRequest().body(savedHolidayStatus);
        else if (savedHolidayStatus.toLowerCase().contains("not saved"))
            return ResponseEntity.internalServerError().body("Error occurred. Data not saved..");

        else return ResponseEntity.ok(savedHolidayStatus);
    }

    @PreAuthorize("@auth.allow('GET_HOLIDAY_DATA')")
    @GetMapping("/getHolidayCalendar")
    public ResponseEntity<List<Holiday>> getHolidayCalendar() {
        LOGGER.info("EmployeeService:HolidayController:getHolidayCalendar");
        List<Holiday> listOfHolidays = holidayService.getAllHolidays();

        if (listOfHolidays.size() <= 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(listOfHolidays);
    }

    @PreAuthorize("@auth.allow('DOWNLOAD_HOLIDAY_CALENDAR')")
    @GetMapping("/downloadHolidayCalendar")
    public ResponseEntity<String> downloadHolidayCalendar(HttpServletResponse resp) {
        LOGGER.info("EmployeeService:HolidayController:downloadHolidayCalendar");
        String downloadedCalendarStatus = holidayService.downloadHolidayCalendar(resp);

        if (downloadedCalendarStatus.equalsIgnoreCase("Holiday Calendar is unavailable"))
            return new ResponseEntity<String>(downloadedCalendarStatus, HttpStatus.NOT_FOUND);

        else if (downloadedCalendarStatus.equalsIgnoreCase("File not downloaded"))
            return ResponseEntity.internalServerError().body(downloadedCalendarStatus);

        return ResponseEntity.ok(downloadedCalendarStatus);

    }

    @PreAuthorize("@auth.allow('UPDATE_HOLIDAY_DATA_BY_HOLIDAY_ID')")
    @PutMapping("/updateHolidayCalendar/{id}")
    public ResponseEntity<String> updateHolidayCalendar(@PathVariable(value = "id") Integer hId, @RequestParam(value = "holidayName") String holidayName, @RequestParam(value = "date") String date) {
        LOGGER.info("EmployeeService:HolidayController:updateHolidayCalendar");
        String status = holidayService.updateHolidayCalendar(hId, holidayName, date);

        if (status.contains("not updated"))
            return ResponseEntity.internalServerError().body(status);
        return ResponseEntity.ok(status);
    }

    @PreAuthorize("@auth.allow('DELETE_HOLIDAYS_DATA_BY_HOLIDAY_ID')")
    @DeleteMapping("/deleteHolidayById/{id}")
    public ResponseEntity<String> deleteHoliday(@PathVariable("id") Integer id) {
        LOGGER.info("EmployeeService:HolidayController:deleteHoliday");
        String status = holidayService.deleteHolidayFromCalendar(id);

        if (status.contains("not deleted"))
            return new ResponseEntity<String>(status, HttpStatus.NOT_FOUND);

        return new ResponseEntity<String>(status, HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("@auth.allow('GET_HOLIDAY_DATA_BY_ID')")
    @GetMapping("/getHolidayByID/{id}")
    public ResponseEntity<Holiday> getHolidayById(@PathVariable("id") Integer hId) {
        LOGGER.info("EmployeeService:HolidayController:getHolidayById");
        Holiday holiday = holidayService.getHolidayById(hId);

        if (holiday == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(holiday);
    }


}