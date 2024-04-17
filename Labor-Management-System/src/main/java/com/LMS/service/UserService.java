package com.LMS.service;

import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;
import com.LMS.utils.ApiResponse;

public interface UserService {

    ApiResponse registerUser(User user);
    AuthenticationResponseDto authenticateUser(String email, String password);

    void initiatePasswordReset(String email);

    void resetPassword(String token, String newPassword);
    void deleteUserByEmail(String email);
    void updateUserByEmail(String email, User updatedUser);

    ApiResponse generateUserReport(Long id);

}
