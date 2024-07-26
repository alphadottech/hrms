package com.adt.hrms.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adt.hrms.util.ProjectEngagementUtility;
import com.adt.hrms.util.TableDataExtractor;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adt.hrms.model.AVTechnology;
import com.adt.hrms.model.Interview;
import com.adt.hrms.model.InterviewCandidateDetails;
import com.adt.hrms.model.InterviewHistory;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.AVTechnologyRepo;
import com.adt.hrms.repository.InterviewCandidateRepo;
import com.adt.hrms.repository.InterviewHistoryRepo;
import com.adt.hrms.repository.InterviewRepository;
import com.adt.hrms.repository.PositionRepo;
import com.adt.hrms.service.InterviewService;
import com.adt.hrms.ui.InterviewModelDTO;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

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
	
	@Autowired
	private TableDataExtractor dataExtractor;
	
	@Autowired
	InterviewHistoryRepo interviewHistoryRepo;
	
  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


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
		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getInterviewerName())){
			throw new IllegalArgumentException("Invalid Name Details");
		}
// 		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getCandidateName())){
//			throw new IllegalArgumentException("Invalid Candidate Name Details");
//		}
		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getSource())){
			throw new IllegalArgumentException("Invalid Source Details");
		}
		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getClientName())){
			throw new IllegalArgumentException("Invalid Client Name Details");
		}
		if(!ProjectEngagementUtility.validateDescription(intwDTO.getNotes())){
			throw new IllegalArgumentException("Invalid Notes......");
		}
		if(!ProjectEngagementUtility.validateCTC(intwDTO.getWorkExInYears())){
			throw new IllegalArgumentException("Invalid Work Exp.Details");
		}

		if(!ProjectEngagementUtility.validateInteger(intwDTO.getRounds())){
			throw new IllegalArgumentException("Invalid Rounds");
		}
		if(!ProjectEngagementUtility.validateInteger(intwDTO.getMarks())){
			throw new IllegalArgumentException("Invalid Marks");
		}
		if(!ProjectEngagementUtility.validateInteger(intwDTO.getCommunication())){
			throw new IllegalArgumentException("Invalid Communication Marks");
		}
		if(!ProjectEngagementUtility.validateInteger(intwDTO.getEnthusiasm())){
			throw new IllegalArgumentException("Invalid Enthusiasm Marks");
		}
		LOGGER.info("interview details update is proccessing");
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
			Optional<InterviewCandidateDetails> candidateDetails=interviewCandidateRepo.findCandidateIdByEmailId(intwDTO.getEmail());;
			Optional<PositionModel> posDetails = posRepo.findById(position_id);
			Optional<AVTechnology> techDetails = techRepo.findById(tech_id);

		    opt.get().setCandidateName(candidateDetails.get().getCandidateName());
			opt.get().setClientName(intwDTO.getClientName());
			opt.get().setCommunication(intwDTO.getCommunication());
			opt.get().setDate(localDate);
			opt.get().setEnthusiasm(intwDTO.getEnthusiasm());
			opt.get().setInterviewerName(intwDTO.getInterviewerName());
			opt.get().setMarks(intwDTO.getMarks());
			opt.get().setNotes(intwDTO.getNotes());
			opt.get().setOfferAccepted(intwDTO.getOfferAccepted());
			opt.get().setOfferReleased(intwDTO.getOfferReleased());
			// HRMS-102 - start
			opt.get().setStatus(intwDTO.getStatus());
			opt.get().setSource(intwDTO.getSource());
			opt.get().setType(intwDTO.getType());
			opt.get().setWorkExInYears(intwDTO.getWorkExInYears());
			if (posDetails.get() != null) {
				opt.get().setPosition_id(posDetails.get());
			}
			if (techDetails.get() != null) {
				opt.get().setTech_id(techDetails.get());
			}
			
			if (candidateDetails.get() != null) {
				opt.get().setCandidate_id(candidateDetails.get());
			}

			Interview updatedRecord = interviewRepository.save(opt.get());
			LOGGER.info("interview details update saccussefully:="+updatedRecord);

			return updatedRecord;
		}
	}
	// HRMS-66 END

	// **** Added on 31/5/2023 ****
	// HRMS-66 START Added new method
	@Override
	public String saveInterviewNew(InterviewModelDTO intwDTO) {
		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getInterviewerName())){
			throw new IllegalArgumentException("Invalid Name Details");
		}

		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getSource())){
			throw new IllegalArgumentException("Invalid Source Details");
		}
		if(!ProjectEngagementUtility.validateEmployee(intwDTO.getClientName())){
			throw new IllegalArgumentException("Invalid Client Name Details");
		}
		if(!ProjectEngagementUtility.validateDescription(intwDTO.getNotes())){
			throw new IllegalArgumentException("Invalid Notes......");
		}
		if(!ProjectEngagementUtility.validateCTC(intwDTO.getWorkExInYears())){
			throw new IllegalArgumentException("Invalid Work Exp.Details");
		}

		if(!ProjectEngagementUtility.validateInteger(intwDTO.getRounds())){
			throw new IllegalArgumentException("Invalid Rounds");
		}
		if(!ProjectEngagementUtility.validateInteger(intwDTO.getMarks())){
			throw new IllegalArgumentException("Invalid Marks");
		}
		if(!ProjectEngagementUtility.validateInteger(intwDTO.getCommunication())){
			throw new IllegalArgumentException("Invalid Communication Marks");
		}
		if(!ProjectEngagementUtility.validateInteger(intwDTO.getEnthusiasm())){
			throw new IllegalArgumentException("Invalid Enthusiasm Marks");
		}
		LOGGER.info("interview details save data is proccessing");
		Integer candidate_id = intwDTO.getCandidate_id();
		Integer position_id = intwDTO.getPosition_id();
		Integer tech_id = intwDTO.getTech_id();
		
		Optional<InterviewCandidateDetails> candidtateDetails = interviewCandidateRepo.findById(candidate_id);
		Optional<PositionModel> posDetails = posRepo.findById(position_id);
		Optional<AVTechnology> techDetails = techRepo.findById(tech_id);
		String date = intwDTO.getDate();
		LocalDate localDate = LocalDate.parse(date);	
		Optional<InterviewCandidateDetails> interviewCandidateDetails=interviewCandidateRepo.findCandidateIdByEmailId(intwDTO.getEmail());
		 Optional<Interview> interviewCan=	interviewRepository.findByCandidate(intwDTO.getCandidate_id(),intwDTO.getRounds());
		 LOGGER.info("interview data  present in DB: ="+interviewCan);
		 if (interviewCan.isPresent()) {
				LocalDate beforeThreeMonthDate = interviewCan.get().getDate().plusMonths(3);
				LocalDate currentDate = LocalDate.now();
				if (beforeThreeMonthDate.isBefore(currentDate) || beforeThreeMonthDate.isEqual(currentDate)) {
					LOGGER.info("update candidate details:"+beforeThreeMonthDate);
					intwDTO.setInterviewId(interviewCan.get().getInterviewId());
					intwDTO.setCandidateName(interviewCan.get().getCandidateName());
					saveDataInInterviewHistory(intwDTO);
					Interview detailsip = interviewCan.get();
					detailsip.setClientName(intwDTO.getClientName());
					detailsip.setCommunication(intwDTO.getCommunication());
					detailsip.setDate(localDate);
					detailsip.setEnthusiasm(intwDTO.getEnthusiasm());
					detailsip.setInterviewerName(intwDTO.getInterviewerName());
					detailsip.setMarks(intwDTO.getMarks());
					detailsip.setNotes(intwDTO.getNotes());
					detailsip.setOfferAccepted(intwDTO.getOfferAccepted());
					detailsip.setOfferReleased(intwDTO.getOfferReleased());
					detailsip.setStatus(intwDTO.getStatus());
					detailsip.setSource(intwDTO.getSource());
					detailsip.setType(intwDTO.getType());
					detailsip.setWorkExInYears(intwDTO.getWorkExInYears());
					detailsip.setCandidateName(intwDTO.getCandidateName());
					detailsip.setTech_id(techDetails.get());
					detailsip.setPosition_id(posDetails.get());
					interviewRepository.save(detailsip);
					return "saved candidate information";
				} else {

					return "this candidate is not Eligible";
				}
			}
        
		Integer interviewId = null;
		if (interviewCandidateDetails.isPresent()) {
			String sql = "SELECT interview_id FROM employee_schema.interview where candidate_id="
					+ interviewCandidateDetails.get().getCandidateId();
			List<Map<String, Object>> interviewData = dataExtractor.extractDataFromTable(sql);
			for (Map<String, Object> data : interviewData) {
				interviewId = Integer.parseInt(String.valueOf(data.get("interview_id")));
			}
		}
		if (interviewId == null) {
			String sql = "select max(interview_id)+1 as interview_id from employee_schema.interview";
			List<Map<String, Object>> interviewData = dataExtractor.extractDataFromTable(sql);
			for (Map<String, Object> data : interviewData) {
				interviewId = Integer.parseInt(String.valueOf(data.get("interview_id")));
			}
		}
		intwDTO.setInterviewId(interviewId);
		Integer round = intwDTO.getRounds();
		// **** If the interview id and round is not present already, then only save,
		// otherwise return that the record is already present **
		Optional<Interview> opt = interviewRepository.getInterviewDetailByInterviewIdAndRound(interviewId, round);

		if (!opt.isPresent()) {
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
			// HRMS-102 - start
			intwEntity.setStatus(intwDTO.getStatus());
//			intwEntity.setScreeningRound(intwDTO.getScreeningRound());
//			intwEntity.setSelected(intwDTO.getSelected());
			// HRMS-102 - end
			intwEntity.setSource(intwDTO.getSource());
			intwEntity.setType(intwDTO.getType());
			intwEntity.setWorkExInYears(intwDTO.getWorkExInYears());
//			if (candidtateDetails.get() != null) {
			if (candidtateDetails != null && !candidtateDetails.isEmpty()) {
				intwEntity.setCandidate_id(candidtateDetails.get());
				intwEntity.setCandidateName(candidtateDetails.get().getCandidateName());
			}
			if (posDetails != null && !posDetails.isEmpty())
				intwEntity.setPosition_id(posDetails.get());
			if (techDetails != null && !techDetails.isEmpty())
				intwEntity.setTech_id(techDetails.get());

			Interview savedRecord = interviewRepository.save(intwEntity);

			if (savedRecord != null)
				return "saved candidate information";
		}
		return null;
	}
	
	public void saveDataInInterviewHistory(InterviewModelDTO intwDTO) {
		LOGGER.info("save inverview data in history table");
		InterviewHistory interviewHistory =new InterviewHistory();
		String date = intwDTO.getDate();
		LocalDate localDate = LocalDate.parse(date);
		interviewHistory.setCandidateId(intwDTO.getCandidate_id());
		interviewHistory.setInterviewId(intwDTO.getInterviewId());
		interviewHistory.setRounds(intwDTO.getRounds());
		interviewHistory.setClientName(intwDTO.getClientName());
		interviewHistory.setCommunication(intwDTO.getCommunication());
		interviewHistory.setDate(localDate);
		interviewHistory.setEnthusiasm(intwDTO.getEnthusiasm());
		interviewHistory.setInterviewerName(intwDTO.getInterviewerName());
		interviewHistory.setMarks(intwDTO.getMarks());
		interviewHistory.setNotes(intwDTO.getNotes());
		interviewHistory.setOfferAccepted(intwDTO.getOfferAccepted());
		interviewHistory.setOfferReleased(intwDTO.getOfferReleased());	
		interviewHistory.setStatus(intwDTO.getStatus());
		interviewHistory.setSource(intwDTO.getSource());
		interviewHistory.setType(intwDTO.getType());
		interviewHistory.setWorkexInYears(intwDTO.getWorkExInYears());
		interviewHistory.setCandidateName(intwDTO.getCandidateName());
		interviewHistory.setTechId(intwDTO.getTech_id());
		interviewHistory.setPositionId(intwDTO.getPosition_id());	
		interviewHistoryRepo.save(interviewHistory);
		
	}
	// HRMS-66 END

	// HRMS-92 -> START
	@Override
	public List<Interview> SearchByCandidateName(String candidateName) {
		return interviewRepository.findByCandidateName(candidateName);
	}

	@Override
	public List<Interview> SearchBySource(String source) {
		return interviewRepository.findBySource(source);

	}

	@Override
	public List<Interview> SearchByClientName(String clientName) {
		return interviewRepository.findByClientName(clientName);
	}
	// HRMS-92 ->END

	// HRMS-93
	public void listAllInterviewDetailsInExcel(HttpServletResponse responseExcel) throws IOException {
		List<Interview> list = interviewRepository.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Interview Details");
		HSSFRow row = sheet.createRow(0);

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

		int dataRowIndex = 1;

		for (Interview interview : list) {
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
			// HRMS-102 - start
//			dataRow.createCell(14).setCellValue(interview.getScreeningRound());
//			dataRow.createCell(15).setCellValue(interview.getSelected());
			dataRow.createCell(14).setCellValue(interview.getOfferReleased());
			dataRow.createCell(15).setCellValue(interview.getType());
			dataRow.createCell(16).setCellValue(interview.getClientName());
			dataRow.createCell(17).setCellValue(interview.getStatus());
			dataRow.createCell(18).setCellValue(interview.getDate().toString());
			// HRMS-102 - end
			dataRowIndex++;

		}

		ServletOutputStream ops = responseExcel.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
	// HRMS-93

	public void listAllPositionDetailsInExcel(HttpServletResponse responseExcel) throws IOException {
		List<PositionModel> list = posRepo.findAll();

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Position Details");
		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("POSITIONID");
		row.createCell(1).setCellValue("POSITION_NAME");
		row.createCell(2).setCellValue("POSITION_TYPE");
		row.createCell(3).setCellValue("VACANCY");
		row.createCell(4).setCellValue("WORK EXP IN YEARS");
		row.createCell(5).setCellValue("STATUS");
		row.createCell(6).setCellValue("REMOTE");
		row.createCell(7).setCellValue("POSITION_OPEN_DATE");
		row.createCell(8).setCellValue("POSITION_CLOSE_DATE");
		row.createCell(9).setCellValue("TECH STACK");

		int dataRowIndex = 1;

		for (PositionModel position : list) {
			HSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(position.getPositionId());
			dataRow.createCell(1).setCellValue(position.getPositionName());
			dataRow.createCell(2).setCellValue(position.getPositionType().toString());
			dataRow.createCell(3).setCellValue(position.getVacancy().toString());
			dataRow.createCell(4).setCellValue(position.getExperienceInYear().toString());
			dataRow.createCell(5).setCellValue(position.getStatus());
			dataRow.createCell(6).setCellValue(position.getRemote());
			dataRow.createCell(7).setCellValue(position.getPositionOpenDate().toString());
			dataRow.createCell(8).setCellValue(position.getPositionCloseDate().toString());
			dataRow.createCell(9).setCellValue(position.getTechStack().toString());

			dataRowIndex++;

		}

		ServletOutputStream ops = responseExcel.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}

}
