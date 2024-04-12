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
}
