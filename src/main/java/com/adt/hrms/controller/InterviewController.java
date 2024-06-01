package com.adt.hrms.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adt.hrms.model.AVTechnology;
import com.adt.hrms.model.Interview;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.service.AVTechnologyService;
import com.adt.hrms.service.InterviewCandidateService;
import com.adt.hrms.service.InterviewService;
import com.adt.hrms.service.PositionService;
import com.adt.hrms.ui.InterviewModelDTO;
import com.adt.hrms.ui.PositionDateConverter;
import com.adt.hrms.ui.PositionUIModel;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/interview")
public class InterviewController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionService positionService;
    @Autowired
    private InterviewService interviewService;
    @Autowired
    private InterviewCandidateService intwCandidateService;
    @Autowired
    private AVTechnologyService avTechnologyService;


    @PreAuthorize("@auth.allow('GET_ALL_INTERVIEW_POSITION_DETAILS')")
    @GetMapping("/getAllPosition")
    public ResponseEntity<List<PositionUIModel>> getAllPosition() {
        LOGGER.info("Employeeservice:InterviewPosition:getAllPosition info level log message");
        return new ResponseEntity<>(positionService.getAllUIPosition(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_INTERVIEW_POSITION_DETAILS_BY_ID')")
    @GetMapping("/getByPositionId/{id}")
    public ResponseEntity<PositionModel> getByPositionId(@PathVariable("id") Integer id) {
        LOGGER.info("Employeeservice:InterviewPosition:getByPositionId info level log message");
        return new ResponseEntity<PositionModel>(positionService.getPosition(id), HttpStatus.OK);
    }


    @PreAuthorize("@auth.allow('GET_ALL_INTERVIEW_DETAILS')")
    @GetMapping("/getAllInterviewDetails")
    public ResponseEntity<List<Interview>> getAllInterviewDetails() {
        LOGGER.info("Employeeservice:InterviewDetails:getAllInterviewDetails info level log message");
        return new ResponseEntity<>(interviewService.listAllInterviewDetails(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('ROLE_ADMIN')")
    @PutMapping("/updateInterviewDetails")
    public ResponseEntity<String> updateInterviewDetails(@RequestBody Interview interviewRequest) {
        LOGGER.info("Employeeservice:Interview:updateInterviewDetails info level log message");
        return new ResponseEntity<>(interviewService.updateToInterviewDetails(interviewRequest), HttpStatus.OK);
    }


    @PreAuthorize("@auth.allow('SAVE_NEW_INTERVIEW_POSITION')")
    @PostMapping("/savePosition")
    public ResponseEntity<String> savePositionNew(@RequestBody PositionDateConverter pdc) {
        LOGGER.info("Employeeservice:InterviewPosition:savePositionNew info level log message");
        return new ResponseEntity<>(positionService.savePositionNew(pdc), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_ALL_INTERVIEW_POSITION_DETAILS')")
    @GetMapping("/getAllPositionNew")
    public ResponseEntity<List<PositionDateConverter>> getAllPositionNew() {
        LOGGER.info("Employeeservice:InterviewPosition:getAllPositionNew info level log message");
        return new ResponseEntity<>(positionService.getAllPositionNew(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('GET_INTERVIEW_DETAILS_BY_ID_AND_ROUND')")
    @GetMapping("/getInterviewDetailByIdAndRound")
    public ResponseEntity<Interview> getInterviewDetailByIDAndRound(@RequestParam("interviewId") int interviewId,
                                                                    @RequestParam("round") int round) {
        LOGGER.info("Employeeservice:InterviewController:getInterviewDetailByID info level log message");
        Interview interview = interviewService.getInterviewDetailByInterviewIdAndRound(interviewId, round);
        if (interview == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(interview);
    }

    @PreAuthorize("@auth.allow('UPDATE_INTERVIEW_DETAILS_BY_ID_AND_ROUND')")
    @PutMapping("/updateInterviewByIdAndRound")
    public ResponseEntity<String> updateInterviewDetailByInterviewIDAndRound(@RequestBody InterviewModelDTO intwDTO) {

        LOGGER.info("EmployeeService:InterviewController:updateInterviewDetailByInterviewIDAndRound info level log message");

        int interviewIdFromReq = intwDTO.getInterviewId();
        int roundIdFromReq = intwDTO.getRounds();

        Interview interviewFromDB = interviewService.getInterviewDetailByInterviewIdAndRound(interviewIdFromReq, roundIdFromReq);

        if (interviewFromDB == null)
            return new ResponseEntity<String>("Data not found", HttpStatus.NOT_FOUND);

        else {
            Interview updatedInterviewDetail = interviewService.updateInterviewDetailByInterviewIdAndRound(interviewIdFromReq,
                    roundIdFromReq, intwDTO);
            return new ResponseEntity<String>("Interview with ID:- " + updatedInterviewDetail.getInterviewId()
                    + " and round:- " + "" + updatedInterviewDetail.getRounds() + " Updated succesfully",
                    HttpStatus.OK);
        }
    }

    @GetMapping("/alltech")
    public ResponseEntity<List<AVTechnology>> getAllTechnologies() {
        LOGGER.info("EmployeeService:InterviewController:getAllTechnologies info level log message");
        return new ResponseEntity<List<AVTechnology>>(avTechnologyService.getAllTechnology(), HttpStatus.OK);
    }

    @PreAuthorize("@auth.allow('SAVE_NEW_INTERVIEW')")
    @PostMapping("/saveInterviewNew")
    public ResponseEntity<String> saveInterviewNew(@RequestBody InterviewModelDTO intwDto) {
        LOGGER.info("EmployeeService:InterviewController:saveInterviewNew info level log message");
        String status = interviewService.saveInterviewNew(intwDto);
        if (status != null) {
            return ResponseEntity.ok("Interview record saved successfully...");
        }
        return new ResponseEntity<String>("Record already present", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("@auth.allow('SEARCH_INTERVIEW_BY_CANDIDATE_NAME')")
    @GetMapping("/SearchByCandidateName")
    public ResponseEntity<List<Interview>> SearchByCandidateName(@RequestParam("search") String candidateName) {
        LOGGER.info("EmployeeService:interview:SearchByCandidateName info level log message");
        return ResponseEntity.ok(interviewService.SearchByCandidateName(candidateName));
    }

    @PreAuthorize("@auth.allow('SEARCH_INTERVIEW_BY_SOURCE')")
    @GetMapping("/SearchBySource")
    public ResponseEntity<List<Interview>> SearchBySource(@RequestParam("search") String source) {
        LOGGER.info("EmployeeService:interview:SearchBySource info level log message");
        return ResponseEntity.ok(interviewService.SearchBySource(source));
    }

    @PreAuthorize("@auth.allow('SEARCH_INTERVIEW_BY_CLIENT_NAME')")
    @GetMapping("/SearchByClientName")
    public ResponseEntity<List<Interview>> SearchByClientName(@RequestParam("search") String clientName) {
        LOGGER.info("EmployeeService:interview:SearchByClientName info level log message");
        return ResponseEntity.ok(interviewService.SearchByClientName(clientName));
    }


    @PreAuthorize("@auth.allow('EXPORT_INTERVIEW_DETAILS_IN_EXCEL')")
    @GetMapping("/getInterviewDetailsExcel")
    public void getAllInterviewDetailsInExcel(@NotNull HttpServletResponse responseExcel) throws Exception {
        LOGGER.info("Employeeservice:InterviewDetails:getAllInterviewDetailsInExcel info level log message");
        responseExcel.setContentType("application/octet-strem");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=InterviewDetails.xls";
        responseExcel.setHeader(headerKey, headerValue);
        interviewService.listAllInterviewDetailsInExcel(responseExcel);
    }

    @PreAuthorize("@auth.allow('EXPORT_INTERVIEW_POSITION_DETAILS_IN_EXCEL')")
    @GetMapping("/getPositionDetailsExcel")
    public void getAllPositionDetailsInExcel(@NotNull HttpServletResponse responseExcel) throws Exception {
        LOGGER.info("Employeeservice:PositionDetails:getAllPositionDetailsInExcel info level log message");
        responseExcel.setContentType("application/octet-strem");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=PositionDetails.xls";
        responseExcel.setHeader(headerKey, headerValue);
        interviewService.listAllPositionDetailsInExcel(responseExcel);
    }


    @PreAuthorize("('UPDATE_INTERVIEW_POSITION_BY_ID')")
    @PutMapping("/updatePositionNew")
    public ResponseEntity<String> updatePositionNew(@RequestBody PositionDateConverter pdc) {
        LOGGER.info("Employeeservice:InterviewPosition:updatePosition info level log message");
        return new ResponseEntity<>(positionService.updatePositionNew(pdc), HttpStatus.OK);
    }


}
