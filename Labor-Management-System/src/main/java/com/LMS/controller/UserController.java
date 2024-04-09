package com.LMS.controller;

import com.LMS.dto.AuthenticationRequestDto;
import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.service.UserService;
import com.LMS.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        try {
            String registeredUserResponse = userService.registerUser(user);
            ApiResponse<User> response = new ApiResponse<>("Registration successful");
            return ResponseEntity.ok(response);
        } catch (EmailAlreadyExistsException e) {
            ApiResponse<User> response = new ApiResponse<>("Registration Unsuccessful, "+e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto request
    ) {
        return ResponseEntity.ok(userService.authenticateUser(request.getEmail(),request.getPassword()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            userService.initiatePasswordReset(email);
            return ResponseEntity.ok(new ApiResponse<>("Password reset email sent."));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("User not found."));
        } catch (Exception ex) {
            // Log the exception details for debugging purposes
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("An error occurred while processing your request."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok(new ApiResponse<>("Password has been reset successfully."));
        } catch (RuntimeException ex) {
            // Assuming you throw a RuntimeException for invalid tokens or expired tokens
            return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage()));
        } catch (Exception ex) {
            // Log the exception details for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("An error occurred while processing your request."));
        }
    }




}
