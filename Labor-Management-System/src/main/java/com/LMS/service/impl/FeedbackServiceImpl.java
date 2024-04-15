package com.LMS.service.impl;

import com.LMS.entity.Feedback;
import com.LMS.entity.HiredLabour;
import com.LMS.repository.FeedbackRepository;
import com.LMS.repository.HiredLabourRepository;
import com.LMS.service.FeedbackService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private HiredLabourRepository hiredLabourRepository;
    @Override
    public Feedback saveFeedback(Feedback feedback) {
        try {
            logger.info(" saving feedback Request:{}", feedback.getHiredLabour());
            HiredLabour managedHiredLabour = hiredLabourRepository.findById(feedback.getHiredLabour().getId())
                    .orElseThrow(() -> new RuntimeException("HiredLabour not found"));
            Feedback newObject= new  Feedback();
            newObject.setRating(feedback.getRating());
            newObject.setReview(feedback.getReview());
            newObject.setServiceType(feedback.getServiceType());
            newObject.setHiredLabour(feedback.getHiredLabour());
            Feedback savedFeedback = feedbackRepository.save(newObject);
            return savedFeedback;
        } catch (Exception e) {
            logger.error("Error saving feedback", e);
            throw new RuntimeException("Failed to save feedback", e);
        }
    }
    @Override
    public Feedback getFeedback(Long id) {
        try {
            return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException("Feedback not found for ID: " + id));
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve feedback with ID: {}", id, e);
            throw e;
        }
    }
    @Override
    public Feedback updateFeedback(Long id, Feedback feedbackDetails) {
        try {
            Feedback feedback = feedbackRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Feedback not found for ID: " + id));
            feedback.setReview(feedbackDetails.getReview());
            feedback.setRating(feedbackDetails.getRating());
            feedback.setServiceType(feedbackDetails.getServiceType());
            return feedbackRepository.save(feedback);
        } catch (RuntimeException e) {
            logger.error("Failed to update feedback with ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteFeedback(Long id) {
        try {
            Feedback feedback = feedbackRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Feedback not found for ID: " + id));
            feedbackRepository.delete(feedback);
        } catch (RuntimeException e) {
            logger.error("Failed to delete feedback with ID: {}", id, e);
            throw e;
        }
    }
@Override
    public List<Feedback> getFeedbacksByUserId(Long userId) {
        try {
            logger.info("Fetching all feedbacks for user ID: {}", userId);
            return feedbackRepository.findAllFeedbacksByUserId(userId);
        } catch (Exception e) {
            logger.error("Error fetching feedbacks for user ID: {}", userId, e);
            throw new RuntimeException("Failed to fetch feedbacks for user ID: " + userId, e);
        }
    }
    @Override
    public Feedback getFeedbackByHiredLabourId(Long hiredLabourId) {
        try {
            return feedbackRepository.findByHiredLabourId(hiredLabourId)
                    .orElseThrow(() -> new RuntimeException("No feedback found for HiredLabour with ID: " + hiredLabourId));
        } catch (RuntimeException e) {
            logger.error("Failed to retrieve feedback for HiredLabour ID: {}", hiredLabourId, e);
            throw e;
        }
    }
}
