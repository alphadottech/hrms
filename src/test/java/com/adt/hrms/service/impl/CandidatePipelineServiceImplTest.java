package com.adt.hrms.service.impl;

import com.adt.hrms.model.CandidatePipeline;
import com.adt.hrms.repository.CandidatePipelineRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CandidatePipelineServiceImplTest {

    private static final int ID = 1;
    private static final int IntId = 1;

    @InjectMocks
    private CandidatePipelineServiceImpl candidatePipelineService;

    @Mock
    private CandidatePipelineRepo candidatePipelineRepo;

    @Test
    void saveCandidatePipelineDetails() {
        CandidatePipeline candipipeline = givenCandidatePipeline();
        candipipeline.setId(0);
        candipipeline.setInterviewId(0);
        when(candidatePipelineRepo.save(candipipeline)).thenReturn(candipipeline);
        assertEquals(candipipeline.getId() + " CandidatePipeline is Saved", candidatePipelineService.saveCandidatePipelineDetails(candipipeline));
    }

    @Test
    void getAllCandidatePipeline() {
        CandidatePipeline candiPipeline = givenCandidatePipeline();
        List<CandidatePipeline> pipelineList = Collections.singletonList(candiPipeline);
        when(candidatePipelineRepo.findAll()).thenReturn(pipelineList);
        assertEquals(pipelineList, candidatePipelineService.getAllCandidatePipeline());
    }

    @Test
    void updateCandidatePipelineDetails() {
        CandidatePipeline candidatePipeline = givenCandidatePipeline();
        when(candidatePipelineRepo.findById(candidatePipeline.getId())).thenReturn(Optional.of(candidatePipeline));
        when(candidatePipelineRepo.save(candidatePipeline)).thenReturn(candidatePipeline);
        assertEquals(candidatePipeline.getId() + " Details Updated Successfully", candidatePipelineService.updateCandidatePipelineDetails(candidatePipeline));
    }

    @Test
    void getCandidatePipelineById() {
        CandidatePipeline candidatePipeline = givenCandidatePipeline();
        when(candidatePipelineRepo.findById(ID)).thenReturn(Optional.of(candidatePipeline));
        assertEquals(candidatePipeline, candidatePipelineService.getCandidatePipelineById(ID));
    }

    @Test
    void deleteCandidateById() {
        CandidatePipeline candidatePipeline = givenCandidatePipeline();
        when(candidatePipelineRepo.findById(candidatePipeline.getId())).thenReturn(Optional.of(candidatePipeline));
        doNothing().when(candidatePipelineRepo).deleteById(ID);
        assertEquals(candidatePipeline.getId() + " has been Deleted", candidatePipelineService.deleteCandidateById(ID));
    }

    @Test
    void nullDeleteCandidateById() {
        CandidatePipeline candidatePipeline = givenCandidatePipeline();
        when(candidatePipelineRepo.findById(candidatePipeline.getId())).thenReturn(Optional.empty());
        doNothing().when(candidatePipelineRepo).deleteById(ID);
        assertEquals("Invalid Employee Id :: " + candidatePipeline.getId(), candidatePipelineService.deleteCandidateById(ID));
    }

    private CandidatePipeline givenCandidatePipeline() {
        CandidatePipeline candipipeline = new CandidatePipeline();
        candipipeline.setId(ID);
        candipipeline.setInterviewId(IntId);
        return candipipeline;
    }

}