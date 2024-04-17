package com.LMS.service;

import com.LMS.entity.Feedback;
import com.LMS.utils.ApiResponse;

import java.util.List;

public interface FeedbackService {
    Feedback saveFeedback(Feedback feedback);
    Feedback getFeedback(Long id);
    Feedback updateFeedback(Long id, Feedback feedback);
    void deleteFeedback(Long id);
    List<Feedback> getFeedbacksByUserId(Long userId);
    Feedback getFeedbackByHiredLabourId(Long hiredLabourId);
    ApiResponse GetFeedBackReportDetails(Long userId);
}
