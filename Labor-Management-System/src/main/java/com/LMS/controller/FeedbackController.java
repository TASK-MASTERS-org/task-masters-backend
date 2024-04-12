package com.LMS.controller;

import com.LMS.entity.Feedback;
import com.LMS.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> postFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.saveFeedback(feedback);
            return ResponseEntity.ok(savedFeedback);
        } catch (RuntimeException e) {
            log.error("Failed to post feedback", e);
            return ResponseEntity.badRequest().body("Failed to post feedback: " + e.getMessage());
        }
    }
}
