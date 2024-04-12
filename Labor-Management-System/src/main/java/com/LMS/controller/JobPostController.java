package com.LMS.controller;

import com.LMS.entity.JobPost;
import com.LMS.service.JobPostService;
import com.LMS.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job-posts")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;
    private static final Logger logger = LoggerFactory.getLogger(JobPostController.class);

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createJobPost(@RequestBody JobPost jobPost) {
        try {
            logger.info("Attempting to create a job post for user ID {}", jobPost.getUser().getId());
            ApiResponse createdJobPost = jobPostService.createJobPost(jobPost);
            logger.info("Attempting to create a job post for user ID Response {}", jobPost.getUser().getId());

            return ResponseEntity.ok(createdJobPost);

        } catch (Exception e) {
            logger.error("Error creating job post: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(), "Failed to create job post"));
        }
    }

    @GetMapping("/get-all-job-post-by-user")
    public ResponseEntity<ApiResponse> getJobPostsByUserId(@RequestParam Long userId) {
        try {
            logger.info("Fetching all job posts for user ID {}", userId);
            ApiResponse jobPosts = jobPostService.getJobPostsByUserId(userId);
            logger.info("Fetching all job posts for user ID Controller Response:{}", jobPosts);
            return ResponseEntity.ok(jobPosts);
        } catch (Exception e) {
            logger.error("Error fetching job posts for user ID {}: {}", userId, e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(), "Failed to fetch job posts for user"));
        }
    }
}
