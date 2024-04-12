package com.LMS.service.impl;

import com.LMS.controller.UserController;
import com.LMS.entity.JobPost;
import com.LMS.repository.JobPostRepository;
import com.LMS.service.JobPostService;
import com.LMS.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class createJobPostImpl implements JobPostService {
    @Autowired
    private JobPostRepository jobPostRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public ApiResponse createJobPost(JobPost jobPost) {
try {
    logger.info("Create Job post ServiceImpl Request JobPost data:{} ",jobPost);
    JobPost data = jobPostRepository.save(jobPost);
    logger.info("Create Job post ServiceImpl Response-data:{} ",data);
    return new ApiResponse("create Job Post Success", data);
}catch (Exception e){
    logger.error("error while Creating Job Posts",e);
throw  new RuntimeException("error while Creating Job Posts",e);
}
    }
    @Override
    public ApiResponse getJobPostsByUserId(Long userId) {
        try{
            logger.info("Create Job post ServiceImpl Request  userId-:{} ",userId);
            List<JobPost> Data = jobPostRepository.findByUserId(userId);
            logger.info("Create Job post ServiceImpl Response   Data-:{} ",Data);
        return new ApiResponse("Get All Job Posts By UserId Success",Data);
    }catch (Exception e){
            logger.error("error while Fetching job Post By User Id ", e);
            throw  new RuntimeException("error while Fetching job Post By User Id ", e);
        }
    }

    @Override
    public ApiResponse updateJobPost(Long id, JobPost updatedJobPost) {
        logger.info("update Job Postb post ServiceImpl Request  id-:{} JobPost:{} ",id,updatedJobPost);
        return jobPostRepository.findById(id)
                .map(jobPost -> {
                    jobPost.setName(updatedJobPost.getName());
                    jobPost.setDescription(updatedJobPost.getDescription());
                    jobPost.setDate(updatedJobPost.getDate());
                    jobPost.setSkills(updatedJobPost.getSkills());
                    jobPost.setUpper_price(updatedJobPost.getUpper_price());
                    jobPost.setLower_price(updatedJobPost.getLower_price());
                    jobPostRepository.save(jobPost);
                    return new ApiResponse("Job post updated successfully", jobPost);
                }).orElse(new ApiResponse("Job post not found", null));
    }

    @Override
    public ApiResponse deleteJobPost(Long id) {
        logger.info("JJob post deleted request ");
        if (jobPostRepository.existsById(id)) {
            jobPostRepository.deleteById(id);
            return new ApiResponse("Job post deleted successfully", null);
        } else {
            return new ApiResponse("Job post not found", null);
        }
    }

    @Override
    public ApiResponse getJobPostById(Long id) {
        logger.info("Retrieving job post with ID {}", id);
        Optional<JobPost> jobPost = jobPostRepository.findById(id);
        if (jobPost.isPresent()) {
            logger.info("Found job post with ID {}", id);
        } else {
            logger.warn("No job post found with ID {}", id);
        }
        return new ApiResponse("getJobPostById Success",jobPost);
    }


}
