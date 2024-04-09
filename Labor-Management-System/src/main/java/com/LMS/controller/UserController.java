package com.LMS.controller;

import com.LMS.dto.AuthenticationRequestDto;
import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.service.UserService;
import com.LMS.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

}
