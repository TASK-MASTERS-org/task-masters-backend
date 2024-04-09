package com.LMS.service.impl;

import com.LMS.configs.JwtService;
import com.LMS.dto.AuthenticationResponseDto;
import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.repository.UserRepository;
import com.LMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
}
