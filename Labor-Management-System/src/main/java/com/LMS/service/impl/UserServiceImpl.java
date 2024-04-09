package com.LMS.service.impl;

import com.LMS.entity.User;
import com.LMS.exception.EmailAlreadyExistsException;
import com.LMS.repository.UserRepository;
import com.LMS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException(user.getEmail());
                });
        //Hash the password
        String HashedPassword= BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(HashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!password.equals(user.getPassword())) { // Simplified; use a password encoder in real applications
            throw new BadCredentialsException("Invalid credentials");
        }

        return user;
    }
}
