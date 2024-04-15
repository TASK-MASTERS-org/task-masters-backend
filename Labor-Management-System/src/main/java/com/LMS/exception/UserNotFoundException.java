package com.LMS.exception;

public class UserNotFoundException extends  RuntimeException{
    public UserNotFoundException(String email) {
        super("A user with the email: " + email + " NotFound.");
    }

}
