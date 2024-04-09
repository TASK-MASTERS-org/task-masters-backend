package com.LMS.service;

import com.LMS.entity.User;

public interface UserService {
    User registerUser(User user);
    User authenticateUser(String email, String password);
}
