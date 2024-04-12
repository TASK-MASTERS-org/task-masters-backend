package com.LMS.service;

import com.LMS.entity.JobPost;
import com.LMS.utils.ApiResponse;

import java.util.List;

public interface JobPostService {

    ApiResponse createJobPost(JobPost jobPost);
    ApiResponse getJobPostsByUserId(Long userId);
}
