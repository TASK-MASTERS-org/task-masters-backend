package com.LMS.controller;

import com.LMS.dto.AuthenticationRequestDto;
import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.service.UserService;
import com.LMS.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    // Changed Logger declaration to use SLF4J Logger
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {
        try {
            logger.info("Starting user registration for {}", user.getEmail());
            String registeredUserResponse = userService.registerUser(user);
            ApiResponse<User> response = new ApiResponse<>("Registration successful");
            logger.info("User registration completed for {}", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (EmailAlreadyExistsException e) {
            logger.error("Registration unsuccessful for {}: {}", user.getEmail(), e.getMessage());
            ApiResponse<User> response = new ApiResponse<>("Registration Unsuccessful, " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        try {
            logger.info("Authentication request for {}", request.getEmail());
            AuthenticationResponseDto response = userService.authenticateUser(request.getEmail(), request.getPassword());
            logger.info("Authentication successful for {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user. Incorrect credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect email or password. Please try again: " + e.getMessage());
        } catch (AuthenticationException e) {
            logger.error("Authentication failed for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
        catch (Exception e) {
            logger.error("Error processing authentication for {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing authentication request");
        }
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            logger.info("Password reset request initiated for {}", email);
            userService.initiatePasswordReset(email);
            logger.info("Password reset email sent to {}", email);
            return ResponseEntity.ok(new ApiResponse<>("Password reset email sent."));
        } catch (UsernameNotFoundException ex) {
            logger.error("Password reset request failed for {}: User not found", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("User not found."));
        } catch (Exception ex) {
            logger.error("Error processing password reset request for {}: {}", email, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("An error occurred while processing your request."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            logger.info("Password reset initiated with token {}", token);
            userService.resetPassword(token, newPassword);
            logger.info("Password reset successfully with token {}", token);
            return ResponseEntity.ok(new ApiResponse<>("Password has been reset successfully."));
        } catch (RuntimeException ex) {
            logger.error("Password reset failed with token {}: {}", token, ex.getMessage());
            return ResponseEntity.badRequest().body(new ApiResponse<>(ex.getMessage()));
        } catch (Exception ex) {
            logger.error("Error processing password reset with token {}: {}", token, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("An error occurred while processing your request."));
        }

    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam String email) {
        try {
            logger.info("deleteUserByEmail Request {}",email);
            userService.deleteUserByEmail(email);
            logger.info("deleteUserByEmail Request-End ");
            return ResponseEntity.ok().body("User with email " + email + " has been deleted successfully.");
        } catch (Exception e) {
            logger.error("Failed to deleting user:{}", e.getMessage());
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserByEmail(@RequestParam String email, @RequestBody User updatedUser) {
        try {
            logger.info("UpdateUserByEmail Request {}",email);
            userService.updateUserByEmail(email, updatedUser);
            logger.info("deleteUserByEmail Request-End :{} ",updatedUser);
            return ResponseEntity.ok().body("User with email " + email + " updated successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to update user:{}", e.getMessage());
            return ResponseEntity.badRequest().body("Failed to update user: " + e.getMessage());
        }
    }
}
