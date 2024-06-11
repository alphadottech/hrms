package com.adt.hrms.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import com.itextpdf.kernel.geom.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.Holiday;
import com.adt.hrms.repository.HolidayRepository;
import com.adt.hrms.repository.ImageRepository;
import com.adt.hrms.service.HolidayService;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepository holidayRepo;

	@Autowired
	private ImageRepository imgRepo;

	@Override
	public String saveHoliday(String holidayName, String date) {

		List<Holiday> listOfHolidays = holidayRepo.findAll();

		boolean isHolidayPresent = false;
		boolean isDatePresent = false;

		//** Parsing the date from String to LocalDate **
		LocalDate localDate = LocalDate.parse(date);

		//*** Check whether the holiday name or the holiday date needed to be added is present already in the DB or not ***
		for(Holiday holiday : listOfHolidays) {
			if(holiday.getHolidayName().equalsIgnoreCase(holidayName)) {
				isHolidayPresent = true;
				break;
			}
			if(holiday.getDate().equals(localDate)) {
				isDatePresent = true;
				break;
			}
		}

		if(isHolidayPresent) {
			return "Holiday already present in Database";
		}

		else if(isDatePresent) {
			return "Date already present in Database";
		}

		else {
			//** Getting the month from the date **
			String month = localDate.getMonth().toString().substring(0, 1) + localDate.getMonth().toString().substring(1).toLowerCase();
			//*** Getting day from the date **
			String day = localDate.getDayOfWeek().toString().substring(0, 1) + localDate.getDayOfWeek().toString().substring(1).toLowerCase();

			//*** Setting the data in the entity ***
			Holiday holiday = new Holiday();
			holiday.setHolidayName(holidayName);
			holiday.setDate(localDate); 
			holiday.setMonth(month);
			holiday.setDay(day);

			Holiday savedData = holidayRepo.save(holiday);

			if(savedData == null) return "Not saved";
			return "Holiday:- "+holidayName+" saved successfully"; 
		}
	}

	@Override
	public List<Holiday> getAllHolidays() {
		return holidayRepo.findAll();
	}

	@Override
	public String downloadHolidayCalendar(HttpServletResponse resp) {

		List<Holiday> listOfHolidays = holidayRepo.findAll();
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=";
		String fileName = "Holiday_Calendar_";
		String fullFileName = "";

		if (listOfHolidays.size() <= 0) return "Holiday Calendar is unavailable";

		Holiday firstRecord = holidayRepo.getFirstHolidayRecord();
		int year = firstRecord.getDate().getYear();

		fullFileName = fileName + year + ".pdf";
		headerValue += fullFileName;

		ServletOutputStream outputStreamObj = null;
		Document document = null;

		try {
			outputStreamObj = resp.getOutputStream();
			resp.setContentType("application/pdf; charset=utf-8");
			resp.setHeader(headerKey, headerValue);

			PdfWriter pdfWriter = new PdfWriter(outputStreamObj);
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(PageSize.A4);

			document = new Document(pdfDocument);

			// Create the table
			float colWidth[] = {60, 250, 110, 90};
			Table table = new Table(colWidth);

			table.addHeaderCell(new Cell().add("S.No"));
			table.addHeaderCell(new Cell().add("Holiday")).setTextAlignment(TextAlignment.CENTER);
			table.addHeaderCell(new Cell().add("Date")).setTextAlignment(TextAlignment.CENTER);
			table.addHeaderCell(new Cell().add("Day")).setTextAlignment(TextAlignment.CENTER);

			int count = 0;
			for (Holiday holiday : listOfHolidays) {
				table.addCell(new Cell().add(String.valueOf(++count)));
				table.addCell(new Cell().add(holiday.getHolidayName())).setTextAlignment(TextAlignment.CENTER);

				LocalDate localDate = holiday.getDate();
				int date = localDate.getDayOfMonth();
				String month = localDate.getMonth().name();
				table.addCell(new Cell().add(date + " " + month + " " + year)).setTextAlignment(TextAlignment.CENTER);

				table.addCell(new Cell().add(holiday.getDay())).setTextAlignment(TextAlignment.CENTER);
			}

			// Get the image from DB
			byte[] imageFromDB = imgRepo.getImageData();
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageFromDB);
			BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "JPEG", outputStream);
			byte[] convertedImageData = outputStream.toByteArray();

			ImageData imgData = ImageDataFactory.create(convertedImageData);
			Image headerImg = new Image(imgData);
			headerImg.setRelativePosition(160, -70, 40, 0);

			document.add(headerImg);
			document.add(table);

			outputStreamObj.flush();

			return "File has been downloaded successfully";

		} catch (Exception e) {
			e.printStackTrace();
			return "File not downloaded";
		} finally {
			if (document != null) {
				document.close();
			}
			try {
				if (outputStreamObj != null) {
					outputStreamObj.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public String updateHolidayCalendar(Integer hId, String holidayName, String date) {

		Optional<Holiday> holiday = holidayRepo.findById(hId);

		//** Parsing the date from String to LocalDate **
		LocalDate localDate = LocalDate.parse(date);

		//** Getting the month from the date **
		String month = localDate.getMonth().toString().substring(0, 1) + localDate.getMonth().toString().substring(1).toLowerCase();
		//*** Getting day from the date **
		String day = localDate.getDayOfWeek().toString().substring(0, 1) + localDate.getDayOfWeek().toString().substring(1).toLowerCase();

		//*** Setting the data in the entity ***
		holiday.get().setHolidayName(holidayName);
		holiday.get().setDate(localDate);
		holiday.get().setMonth(month);
		holiday.get().setDay(day);

		Holiday savedData = holidayRepo.save(holiday.get());

		if(savedData == null) return "Holiday calendar not updated..";
		return "Holiday calendar updated successfully"; 
	}

	@Override
	public String deleteHolidayFromCalendar(Integer hId) {

		Optional<Holiday> holiday = holidayRepo.findById(hId);

		if(!holiday.isPresent()) {
			return "Holiday not present";
		}
		holidayRepo.delete(holiday.get());
		return "Holiday deleted successfully";

	}

	@Override
	public Holiday getHolidayById(Integer hId) {
		Optional<Holiday> holiday = holidayRepo.findById(hId);

		if(!holiday.isPresent())
			return null;

		return holiday.get();
	}


}