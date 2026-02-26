package com.worknest.dto;

import lombok.Data;

@Data
public class AuthResponse {

    private String token;
    private String message;

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // getters
    
//    public String getToken() {
//        return token;
//    }
//    
//    public String getMessage() {
//        return message;
//    }
}