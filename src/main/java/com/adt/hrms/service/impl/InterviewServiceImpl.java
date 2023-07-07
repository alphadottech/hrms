package com.adt.hrms.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.AVTechnology;
import com.adt.hrms.model.Interview;
import com.adt.hrms.model.InterviewCandidateDetails;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.AVTechnologyRepo;
import com.adt.hrms.repository.InterviewCandidateRepo;
import com.adt.hrms.repository.InterviewRepository;
import com.adt.hrms.repository.PositionRepo;
import com.adt.hrms.service.InterviewService;
import com.adt.hrms.ui.InterviewModelDTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;
	@Autowired
	private InterviewCandidateRepo interviewCandidateRepo;
	@Autowired
	private PositionRepo posRepo;
	@Autowired
	private AVTechnologyRepo techRepo;

	@Override
	public String saveInterview(Interview in) {
		interviewRepository.save(in);
		return " New interview ID is Saved";

	}

	@Override
	public List<Interview> listAllInterviewDetails() {
		List<Interview> list = interviewRepository.findAll();
		return list;
	}

	@Override
	public String updateToInterviewDetails(Interview emp) {
		return interviewRepository.save(emp).getInterviewId() + " Details Updated Successfully!";
	}

	@Override
	public Interview getInterviewDetailsById(Integer empId) {
		Optional<Interview> opt = interviewRepository.findById(empId);
		if (opt.isPresent())
			return opt.get();
		else
			return null;
	}

	@Override
	public Interview getInterviewDetailByInterviewIdAndRound(Integer interviewId, Integer round) {
		Optional<Interview> optional = interviewRepository.getInterviewDetailByInterviewIdAndRound(interviewId, round);
		if (!optional.isPresent())
			return null;
		return optional.get();
	}

	// HRMS-66 START Added new method
	@Override
	public Interview updateInterviewDetailByInterviewIdAndRound(Integer interviewId, Integer round,
			InterviewModelDTO intwDTO) {
		// TODO Auto-generated method stub

		Optional<Interview> opt = interviewRepository.getInterviewDetailByInterviewIdAndRound(interviewId, round);

		if (!opt.isPresent())
			return null;

		else {

			// *** Get the date from the request payload, and convert into LocalDate
			String date = intwDTO.getDate();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate localDate = LocalDate.parse(date);

			Integer position_id = intwDTO.getPosition_id();
			Integer tech_id = intwDTO.getTech_id();

			Optional<PositionModel> posDetails = posRepo.findById(position_id);
			Optional<AVTechnology> techDetails = techRepo.findById(tech_id);

			// opt.get().setCandidateName(intw.getCandidateName());
			opt.get().setClientName(intwDTO.getClientName());
			opt.get().setCommunication(intwDTO.getCommunication());
			opt.get().setDate(localDate);
			opt.get().setEnthusiasm(intwDTO.getEnthusiasm());
			opt.get().setInterviewerName(intwDTO.getInterviewerName());
			opt.get().setMarks(intwDTO.getMarks());
			opt.get().setNotes(intwDTO.getNotes());
			opt.get().setOfferAccepted(intwDTO.getOfferAccepted());
			opt.get().setOfferReleased(intwDTO.getOfferReleased());
			opt.get().setScreeningRound(intwDTO.getScreeningRound());
			opt.get().setSelected(intwDTO.getSelected());
			opt.get().setSource(intwDTO.getSource());
			opt.get().setType(intwDTO.getType());
			opt.get().setWorkExInYears(intwDTO.getWorkExInYears());
			if (posDetails.get() != null) {
				opt.get().setPosition_id(posDetails.get());
			}
			if (techDetails.get() != null) {
				opt.get().setTech_id(techDetails.get());
			}

			Interview updatedRecord = interviewRepository.save(opt.get());

			return updatedRecord;
		}
	}
	// HRMS-66 END

	// **** Added on 31/5/2023 ****
	// HRMS-66 START Added new method
	@Override
	public String saveInterviewNew(InterviewModelDTO intwDTO) {

		Integer interviewId = intwDTO.getInterviewId();
		Integer round = intwDTO.getRounds();

		// **** If the interview id and round is not present already, then only save,
		// otherwise return that the record is already present **
		Optional<Interview> opt = interviewRepository.getInterviewDetailByInterviewIdAndRound(interviewId, round);

		if (!opt.isPresent()) {

			Integer candidate_id = intwDTO.getCandidate_id();
			Integer position_id = intwDTO.getPosition_id();
			Integer tech_id = intwDTO.getTech_id();

			// ** Converting the date from request into LocalDate ***
			String date = intwDTO.getDate();
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			LocalDate localDate = LocalDate.parse(date);

			Optional<InterviewCandidateDetails> candidtateDetails = interviewCandidateRepo.findById(candidate_id);
			Optional<PositionModel> posDetails = posRepo.findById(position_id);
			Optional<AVTechnology> techDetails = techRepo.findById(tech_id);

			Interview intwEntity = new Interview();
			// *** Setting the values from DTO to the entity ****
			intwEntity.setInterviewId(intwDTO.getInterviewId());
			intwEntity.setRounds(intwDTO.getRounds());
			intwEntity.setClientName(intwDTO.getClientName());
			intwEntity.setCommunication(intwDTO.getCommunication());

			intwEntity.setDate(localDate);

			intwEntity.setEnthusiasm(intwDTO.getEnthusiasm());
			intwEntity.setInterviewerName(intwDTO.getInterviewerName());
			intwEntity.setMarks(intwDTO.getMarks());
			intwEntity.setNotes(intwDTO.getNotes());
			intwEntity.setOfferAccepted(intwDTO.getOfferAccepted());
			intwEntity.setOfferReleased(intwDTO.getOfferReleased());
			intwEntity.setScreeningRound(intwDTO.getScreeningRound());
			intwEntity.setSelected(intwDTO.getSelected());
			intwEntity.setSource(intwDTO.getSource());
			intwEntity.setType(intwDTO.getType());
			intwEntity.setWorkExInYears(intwDTO.getWorkExInYears());
			if (candidtateDetails.get() != null) {
				intwEntity.setCandidate_id(candidtateDetails.get());
				intwEntity.setCandidateName(candidtateDetails.get().getCandidateName());
			}
			if (posDetails.get() != null)
				intwEntity.setPosition_id(posDetails.get());
			if (techDetails.get() != null)
				intwEntity.setTech_id(techDetails.get());

			Interview savedRecord = interviewRepository.save(intwEntity);

			if (savedRecord != null)
				return "saved";
		}
		return null;
	}
	// HRMS-66 END

	// HRMS-93
	public void listAllInterviewDetailsInExcel(HttpServletResponse responseExcel) throws IOException {
		List<Interview> list = interviewRepository.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Interview Details");
		HSSFRow row =  sheet.createRow(0);

		row.createCell(0).setCellValue("INTERVIEWID");
		row.createCell(1).setCellValue("ROUNDS");
		row.createCell(2).setCellValue("TECHID");
		row.createCell(3).setCellValue("POSITION ID");
		row.createCell(4).setCellValue("CANDIDATE ID");
		row.createCell(5).setCellValue("MARKS");
		row.createCell(6).setCellValue("COMMUNICATION");
		row.createCell(7).setCellValue("ENTHUSIASM");
		row.createCell(8).setCellValue("NOTES");
		row.createCell(9).setCellValue("WORK EXP IN YEARS");
		row.createCell(10).setCellValue("INTERVIEWER NAME");
		row.createCell(11).setCellValue("CANDIDATE NAME");
		row.createCell(12).setCellValue("SOURCE");
		row.createCell(13).setCellValue("OFFER ACCEPTED");
		row.createCell(14).setCellValue("SCREENING ROUND");
		row.createCell(15).setCellValue("SELECTED");
		row.createCell(16).setCellValue("OFFER RELEASED");
		row.createCell(17).setCellValue("TYPE");
		row.createCell(18).setCellValue("CLIENT NAME");
		row.createCell(19).setCellValue("DATE");

		int dataRowIndex =1;

		for(Interview interview : list ){
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(interview.getInterviewId());
			dataRow.createCell(1).setCellValue(interview.getRounds());
			dataRow.createCell(2).setCellValue(interview.getTech_id().toString());
			dataRow.createCell(3).setCellValue(interview.getPosition_id().toString());
			dataRow.createCell(4).setCellValue(interview.getCandidate_id().toString());
			dataRow.createCell(5).setCellValue(interview.getMarks());
			dataRow.createCell(6).setCellValue(interview.getCommunication());
			dataRow.createCell(7).setCellValue(interview.getEnthusiasm());
			dataRow.createCell(8).setCellValue(interview.getNotes());
			dataRow.createCell(9).setCellValue(interview.getWorkExInYears());
			dataRow.createCell(10).setCellValue(interview.getInterviewerName());
			dataRow.createCell(11).setCellValue(interview.getCandidateName());
			dataRow.createCell(12).setCellValue(interview.getSource());
			dataRow.createCell(13).setCellValue(interview.getOfferAccepted());
			dataRow.createCell(14).setCellValue(interview.getScreeningRound());
			dataRow.createCell(15).setCellValue(interview.getSelected());
			dataRow.createCell(16).setCellValue(interview.getOfferReleased());
			dataRow.createCell(17).setCellValue(interview.getType());
			dataRow.createCell(18).setCellValue(interview.getClientName());
			dataRow.createCell(19).setCellValue(interview.getDate().toString());
			dataRowIndex++;

		}

		ServletOutputStream ops = responseExcel.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
	// HRMS-93

}
