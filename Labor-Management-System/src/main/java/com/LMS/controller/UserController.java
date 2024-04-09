package com.LMS.controller;

import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.service.UserService;
import com.LMS.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        ApiResponse<User> response = new ApiResponse<>("Registration successful.", registeredUser);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User authenticatedUser = userService.authenticateUser(email, password);
        ApiResponse<User> response = new ApiResponse<>("Login successful.", authenticatedUser);

        return ResponseEntity.ok(response);
    }


    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        // You can customize this response as needed
        ApiResponse<User> response = new ApiResponse<>("Registration Unsuccessful "+ e.getMessage(), null);

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
