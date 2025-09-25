package com.hitachi.mobile.dto;

import jakarta.validation.constraints.*;

public class SimValidationRequest {

    @NotBlank(message = "SIM number is required")
    @Pattern(regexp = "\\d{13}", message = "SIM number must be 13 digits")
    private String simNumber;

    @NotBlank(message = "Service number is required")
    @Pattern(regexp = "\\d{10}", message = "Service number must be 10 digits")
    private String serviceNumber;

    // Constructors
    public SimValidationRequest() {}

    public SimValidationRequest(String simNumber, String serviceNumber) {
        this.simNumber = simNumber;
        this.serviceNumber = serviceNumber;
    }

    // Getters and Setters
    public String getSimNumber() { return simNumber; }
    public void setSimNumber(String simNumber) { this.simNumber = simNumber; }

    public String getServiceNumber() { return serviceNumber; }
    public void setServiceNumber(String serviceNumber) { this.serviceNumber = serviceNumber; }
}