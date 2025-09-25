package com.hitachi.mobile.dto;

public class LoginResponse {
    private String token;
    private String message;
    private String tokenType = "Bearer";

    public LoginResponse() {}

    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
}
