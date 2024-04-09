package com.LMS.service;

import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;

public interface UserService {

    String registerUser(User user);
    AuthenticationResponseDto authenticateUser(String email, String password);
}
