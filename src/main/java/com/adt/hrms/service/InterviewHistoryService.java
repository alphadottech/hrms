package com.adt.hrms.service;

import com.adt.hrms.model.Interview;
import com.adt.hrms.model.InterviewHistory;

import java.util.List;

public interface InterviewHistoryService {
    List<InterviewHistory> getAllInterviewHistoryByInterviewId(Integer interviewId);

}
