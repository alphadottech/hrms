package com.adt.hrms.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

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

		//*** Getting list of holidays ***
		List<Holiday> listOfHolidays = holidayRepo.findAll();

		//*** Set the header key and header value ***
		String headerKey ="Content-Disposition";
		String headerValue = "attachment;filename=";

		String fileName = "Holiday_Calendar_";
		String fullFileName = "";

		if(listOfHolidays.size() <= 0) return "Holiday Calendar is unavailable";

		else {

			//*** Get the year in the Database ***
			Holiday firstRecord = holidayRepo.getFirstHolidayRecord();

			int year = firstRecord.getDate().getYear();

			//*** Dynamically adjusting File name of holiday with the current year in DB ***
			fullFileName = fileName + year +".pdf";

			headerValue += fullFileName;

			ServletOutputStream outputStreamObj = null;
			Document document = null;


			//** Writing into the PDF file **
			try {

				/* If we want to download the file in other systems, we need to use the HttpServletResponse obj
				 * and get the output stream of it and pass it in PdfWriter obj.
				 * 
				 *  Moreover, we need to give the filename in header and set the content type as "application/pdf"
				 *  and set the header by giving:-  String headerKey ="Content-Disposition";
				                                            String headerValue = "attachment;filename=AnyFileName";
				 */

				//*** Get the ServletOutputStream obj ***
				outputStreamObj = resp.getOutputStream();

				//*** Set the content type of the response ***
				resp.setContentType("application/pdf; charset=utf-8");
				//*** Set the header ***
				resp.setHeader(headerKey, headerValue);

				//** Pass the ServletOutputStream obj ***
				PdfWriter pdfWr = new PdfWriter(outputStreamObj);

				float colWidth[] = { 60, 250, 110, 90 };
				Table table = new Table(colWidth);
				table.addCell(new Cell().add("S.No"));
				table.addCell(new Cell().add("Holiday")).setTextAlignment(TextAlignment.CENTER);
				table.addCell(new Cell().add("Date")).setPaddingLeft(300f);
				table.addCell(new Cell().add("Day")).setPaddingLeft(300f);

				int count = 0;

				for(int i=0; i<listOfHolidays.size(); i++) {

					//** Adjust the serial number in PDF ***
					table.addCell(new Cell().add(""+ ++count));

					//*** Set Holiday name in PDF ***
					table.addCell(new Cell().add(listOfHolidays.get(i).getHolidayName())).setTextAlignment(TextAlignment.CENTER);

					//*** Customize the date and then set the data in PDF ***
					LocalDate localDate = listOfHolidays.get(i).getDate();
					int date = localDate.getDayOfMonth();
					String month = listOfHolidays.get(i).getMonth();
					table.addCell(new Cell().add(date+" "+month+" "+year)).setPaddingLeft(300f);

					//*** Set the day of week in PDF ***
					table.addCell(new Cell().add(listOfHolidays.get(i).getDay())).setPaddingLeft(300f);
				}

				table.setRelativePosition(0, -60, 0, 0); //left top right bottom

				//*** Getting the image from DB ***
				byte[] imageFromDB = imgRepo.getImageData();

				// Create a BufferedImage from the image data
				ByteArrayInputStream byteArr = new ByteArrayInputStream(imageFromDB);
				BufferedImage bufferedImg = ImageIO.read(byteArr);

				// Convert the BufferedImage to the desired format (say JPEG)
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(255);
				ImageIO.write(bufferedImg, "JPEG", outputStream);
				byte[] convertedImageData = outputStream.toByteArray();

				//** Now create the image ***
				ImageData imgData = ImageDataFactory.createJpeg(convertedImageData);
				Image headerImg = new Image(imgData);
				//				headerImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
				headerImg.setRelativePosition(160, -70, 40, 0); //left top right bottom

				PdfDocument pdfDocument = new PdfDocument(pdfWr);
				pdfDocument.setDefaultPageSize(com.itextpdf.kernel.geom.PageSize.A4);

				document = new Document(pdfDocument);
				//document.setMargins(20, 30, 20, 40); //top, right, bottom, left

				document.add(headerImg);
				document.add(table);

				//*** Flush the output stream ***
				outputStreamObj.flush();

				return "File has been downloaded successfully";

			}catch(Exception e) {
				e.printStackTrace();
				return "File not downloaded";
			}finally {

				if(document != null)
					document.close();

				try {
					if(outputStreamObj != null)
						outputStreamObj.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
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