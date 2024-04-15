package com.LMS.controller;

import com.LMS.entity.Feedback;
import com.LMS.service.FeedbackService;
import com.LMS.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/feedback")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<?> postFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.saveFeedback(feedback);
            return ResponseEntity.ok(savedFeedback);
        } catch (RuntimeException e) {
            logger.error("Failed to post feedback", e);
            return ResponseEntity.badRequest().body("Failed to post feedback: " + e.getMessage());
        }
    }
    @GetMapping("/")
    public ResponseEntity<?> getFeedback(@RequestParam Long id) {
        try {
            logger.info("Fetching feedback with ID: {}", id);
            Feedback feedback = feedbackService.getFeedback(id);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            logger.error("Error retrieving feedback with ID: {}", id, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/")
    public ResponseEntity<ApiResponse> updateFeedback(@RequestParam Long id, @RequestBody Feedback feedback) {
        try {
            logger.info("Updating feedback with ID: {}", id);
            Feedback updatedFeedback = feedbackService.updateFeedback(id, feedback);
            return ResponseEntity.ok(new ApiResponse<>("update Feedback Response Success",updatedFeedback));
        } catch (RuntimeException e) {
            logger.error("Error updating feedback with ID: {}", id, e);
           return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteFeedback(@RequestParam Long id) {
        try {
            logger.info("Deleting feedback with ID: {}", id);
            String massage= " Deleting feedback with ID"+ id;
            feedbackService.deleteFeedback(id);
            ApiResponse response= new ApiResponse<>(massage,null);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Error deleting feedback with ID: {}", id, e);
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getFeedbacksByUserId(@RequestParam Long userId) {
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
            return ResponseEntity.ok(new ApiResponse<>("Get Feedbacks By UserId Success",feedbacks));
        } catch (Exception e) {
            logger.error("Failed to get feedbacks for user ID: {}", userId, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/hiredLabour")
    public ResponseEntity<ApiResponse> getFeedbackByHiredLabourId(@RequestParam Long hiredLabourId) {
        try {

            Feedback feedback = feedbackService.getFeedbackByHiredLabourId(hiredLabourId);
            return ResponseEntity.ok(new ApiResponse<>("Get feedback by labour hired ID success",feedback) );

        } catch (Exception e) {
            logger.error("Failed to get getFeedbackByHiredLabourId for user ID: {}", hiredLabourId, e);
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(), null));
        }

    }

}
