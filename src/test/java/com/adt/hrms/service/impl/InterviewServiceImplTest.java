package com.adt.hrms.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.adt.hrms.model.AVTechnology;
import com.adt.hrms.model.Interview;
import com.adt.hrms.model.InterviewCandidateDetails;
import com.adt.hrms.model.PositionModel;
import com.adt.hrms.repository.AVTechnologyRepo;
import com.adt.hrms.repository.InterviewCandidateRepo;
import com.adt.hrms.repository.InterviewRepository;
import com.adt.hrms.repository.PositionRepo;
import com.adt.hrms.ui.InterviewModelDTO;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InterviewServiceImplTest {

	@InjectMocks
	private InterviewServiceImpl interviewService;

	@Mock
	private InterviewRepository interviewRepository;

	@Mock
	private InterviewCandidateRepo interviewCandidateRepo;

	@Mock
	private PositionRepo posRepo;

	@Mock
	private AVTechnologyRepo techRepo;

	final String name = " Sunali";

	final String email = "sunalisingh.adt@gmail.com";

	final String source = "hirect";
	final String clientName = "ABC";

	@Test
	@DisplayName("JUnit test for listAllInterviewDetails()")
	public void listAllInterviewDetails() {

		Interview interview = Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali").clientName("TCS")
				.communication(80).enthusiasm(100).build();
		List<Interview> interviewList = Collections.singletonList(interview);

		when(interviewRepository.findAll()).thenReturn(interviewList);
		assertEquals(interviewList, interviewService.listAllInterviewDetails());

	}

	@Test
	@DisplayName("JUnit test for updateToInterviewDetails()")
	public void updateToInterviewDetails() {

		Interview interview = Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali").clientName("TCS")
				.communication(80).enthusiasm(100).build();

		when(interviewRepository.save(interview)).thenReturn(interview);
		assertEquals(interview.getInterviewId() + " Details Updated Successfully!",
				interviewService.updateToInterviewDetails(interview));

	}

	@Test
	@DisplayName("JUnit test for saveInterview()")
	public void saveInterviewNew() {

		Optional<InterviewModelDTO> interview = Optional
				.ofNullable(InterviewModelDTO.builder().candidate_id(14).candidateName("Sunali").clientName("ABC")
						.communication(98).interviewId(1).rounds(2).position_id(2).build());

		Interview interviewM = Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali").clientName("TCS")
				.communication(80).enthusiasm(100).build();

		when(interviewRepository.getInterviewDetailByInterviewIdAndRound(1, 2)).thenReturn(Optional.of(interviewM));
		assertNull(interviewService.saveInterviewNew(interview.get()));

	}

	@Test
	public void SearchByCandidateName() {
		Interview interview = Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali").clientName("TCS")
				.communication(80).enthusiasm(100).build();

		List<Interview> interviewList = Collections.singletonList(interview);

		when(interviewRepository.findByCandidateName(name)).thenReturn(interviewList);
		assertEquals(interviewList, interviewService.SearchByCandidateName(name));

	}

	@Test
	public void SearchBySource() {
		Interview interview = Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali").clientName("TCS")
				.communication(80).enthusiasm(100).build();

		List<Interview> interviewList = Collections.singletonList(interview);

		when(interviewRepository.findBySource(source)).thenReturn(interviewList);
		assertEquals(interviewList, interviewService.SearchBySource(source));

	}

	@Test
	public void SearchByClientName() {
		Interview interview = Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali").clientName("TCS")
				.communication(80).enthusiasm(100).build();

		List<Interview> interviewList = Collections.singletonList(interview);

		when(interviewRepository.findByClientName(clientName)).thenReturn(interviewList);
		assertEquals(interviewList, interviewService.SearchByClientName(clientName));

	}

	/*
	 * @Test
	 * 
	 * @DisplayName("JUnit test for getInterviewDetailByInterviewIdAndRound()")
	 * public void should_return_interview_according_to_id_and_round() {
	 * 
	 * AVTechnology avTechnology =
	 * AVTechnology.builder().techId(100).description("Java").build();
	 * 
	 * InterviewCandidateDetails
	 * candidate_id=InterviewCandidateDetails.builder().candidateId(14).address(
	 * "Indore").
	 * candidateName("Sunali").contactNo("878978978").cvShortlisted(true).emailId(
	 * "sunalisingh.adt@gmail.com"). highestQualification("B.E").lastCTC(4).
	 * noticePeriod(30).dob(LocalDate.parse("2019-06-06")).build();
	 * 
	 * Interview interview =
	 * Interview.builder().interviewId(1000).rounds(2).candidateName("Sunali")
	 * .clientName("TCS").communication(80).enthusiasm(100).candidate_id(
	 * candidate_id).tech_id(avTechnology).build();
	 * 
	 * when(interviewRepository.getInterviewDetailByInterviewIdAndRound(1,
	 * 2)).thenReturn(Optional.of(interview)); assertEquals(interview,
	 * interviewService.getInterviewDetailByInterviewIdAndRound(1, 2));
	 * 
	 * }
	 */

	/*
	 * @Test
	 * 
	 * @DisplayName("JUnit test for getInterviewDetailsById()") public void
	 * should_return_interview_id() {
	 * 
	 * AVTechnology avTechnology =
	 * AVTechnology.builder().techId(100).description("Java").build();
	 * 
	 * InterviewCandidateDetails
	 * candidate_id=InterviewCandidateDetails.builder().candidateId(14).address(
	 * "Indore").
	 * candidateName("Sunali").contactNo("878978978").cvShortlisted(true).emailId(
	 * "sunalisingh.adt@gmail.com"). highestQualification("B.E").lastCTC(4).
	 * noticePeriod(30).dob(LocalDate.parse("2019-06-06")).build();
	 * 
	 * 
	 * PositionModel position = PositionModel.builder().experienceInYear("2-4")
	 * .positionId(1).positionName("Software Engineer").vacancy(2).build();
	 * 
	 * Interview interview =
	 * Interview.builder().interviewId(2).rounds(1).candidateName("Sunali")
	 * .clientName("TCS").communication(80).enthusiasm(100).candidate_id(
	 * candidate_id).tech_id(avTechnology).position_id(position).build();
	 * 
	 * 
	 * when(interviewRepository.findById(1)).thenReturn(Optional.of(interview));
	 * assertEquals(interview, interviewService.getInterviewDetailsById(1));
	 * 
	 * }
	 */

}
