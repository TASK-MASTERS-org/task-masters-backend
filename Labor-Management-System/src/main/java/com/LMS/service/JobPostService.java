package com.LMS.service;

import com.LMS.entity.JobPost;
import com.LMS.utils.ApiResponse;

import java.util.List;
import java.util.Map;

public interface JobPostService {

    ApiResponse createJobPost(JobPost jobPost);
    ApiResponse getJobPostsByUserId(Long userId);

    ApiResponse updateJobPost(Long id, JobPost jobPost);
    ApiResponse deleteJobPost(Long id);
    ApiResponse getJobPostById(Long j_Id);
    ApiResponse GetJobPostReportDetails(Long userId);


}
