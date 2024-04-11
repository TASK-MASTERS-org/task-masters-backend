package com.LMS.service.impl;

import com.LMS.configs.JwtService;
import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.repository.UserRepository;
import com.LMS.service.EmailService;
import com.LMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private   JwtService jwtService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Override
    public String registerUser(User user) {
        try {
            userRepository.findByEmail(user.getEmail())
                    .ifPresent(u -> {
                        throw new EmailAlreadyExistsException(user.getEmail());
                    });

            String jwtToken = jwtService.generateToken(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return jwtToken;
        } catch (EmailAlreadyExistsException e) {
            // Handle specific case where the email already exists
            // Log the error or handle it as per your application's requirement
            // For example, logging and then rethrowing or returning a specific error message/token
//            logger.error("Registration failed: Email already exists - {}", e.getMessage());
            throw e; // Rethrow if you want the exception to be handled further up the chain
        } catch (Exception e) {
            // Handle other unexpected exceptions
//            logger.error("An unexpected error occurred during registration: {}", e.getMessage());
            // Depending on your application's design, you might want to throw a custom exception here
            throw new RuntimeException("Registration failed due to an unexpected error");
        }
    }

    @Override
    public AuthenticationResponseDto authenticateUser(String email, String password) {
        //FirstStep
        //We need to validate our request (validate whether password & username is correct)
        //Verify whether user present in the database
        //Which AuthenticationProvider -> DaoAuthenticationProvider (Inject)
        //We need to authenticate using authenticationManager injecting this authenticationProvider
        //SecondStep
        //Verify whether userName and password is correct => UserNamePasswordAuthenticationToken
        //Verify whether user present in db
        //generateToken
        //Return the token

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        var user = userRepository.findByEmail(email).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return  AuthenticationResponseDto.builder().accessToken(jwtToken).build();
    }


    @Override
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setTokenExpirationDate(LocalDateTime.now().plusMinutes(30)); // Token expires in 30 minutes
        userRepository.save(user);

        // Send the token to the user's email. Implement your email service to handle email sending.
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), token); // Assume this method exists in your email service
        } catch (Exception e) {
            // Handle email sending failure
            System.out.println(e);
            throw new RuntimeException("Failed to send reset email");
        }
    }
    @Override
    public void resetPassword(String token, String newPassword) {
        // Retrieve the token from the database and verify it
        // Ensure it's not expired and is valid
        // For example: PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));
//
//        User user = resetToken.getUser();
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        user.setPassword(encodedPassword);
//        userRepository.save(user);

        // Optionally, invalidate the token after use
        try {
            // Attempt to retrieve the user by the reset token
            User user = userRepository.findByResetToken(token)
                    .orElseThrow(() -> new RuntimeException("Invalid token"));

            // Check if the token is expired
            if (user.getTokenExpirationDate().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Token expired");
            }

            // Encode the new password and set it to the user
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null); // Invalidate the reset token
            user.setTokenExpirationDate(null); // Clear the expiration date

            // Save the user with the new password, and token information cleared
            userRepository.save(user);
        } catch (RuntimeException e) {
            // Handle runtime exceptions, such as invalid token or token expired
            // This can be logged and/or rethrown to be handled further up the call stack
            System.out.println(e);
            throw e;
        } catch (Exception e) {
            System.out.println(e);
            // Catch any other exceptions that were not anticipated
            // Log the error and perhaps wrap it in a custom exception that's more informative
            throw new RuntimeException("Error resetting password", e);
        }
    }


}
