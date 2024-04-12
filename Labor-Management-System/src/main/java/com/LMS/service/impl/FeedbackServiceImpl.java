package com.LMS.service.impl;

import com.LMS.entity.Feedback;
import com.LMS.repository.FeedbackRepository;
import com.LMS.service.FeedbackService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback saveFeedback(Feedback feedback) {
        try {
            logger.info(" saving feedback Request:{}", feedback);
            Feedback savedFeedback = feedbackRepository.save(feedback);
            return savedFeedback;
        } catch (Exception e) {
            logger.error("Error saving feedback", e);
            throw new RuntimeException("Failed to save feedback", e);
        }
    }
}
