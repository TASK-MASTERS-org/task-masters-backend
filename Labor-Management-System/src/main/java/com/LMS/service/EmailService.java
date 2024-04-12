package com.LMS.service;

public interface EmailService {
    void sendPasswordResetEmail(String to, String token) throws Exception;
}
