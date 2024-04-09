package com.LMS.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    // Getters and Setters
    private String message;
    private T data;
    private String AccessToken;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }
    public ApiResponse(String message, String AccessToken) {
        this.message = message;
        this.AccessToken = AccessToken;
    }
    public ApiResponse(String message) {
        this.message = message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}

