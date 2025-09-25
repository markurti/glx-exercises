package com.hitachi.mobile.dto;

public class SimValidationRequest {
    private String simNumber;
    private String serviceNumber;

    public SimValidationRequest() {}

    public SimValidationRequest(String simNumber, String serviceNumber) {
        this.simNumber = simNumber;
        this.serviceNumber = serviceNumber;
    }

    public String getSimNumber() { return simNumber; }
    public void setSimNumber(String simNumber) { this.simNumber = simNumber; }

    public String getServiceNumber() { return serviceNumber; }
    public void setServiceNumber(String serviceNumber) { this.serviceNumber = serviceNumber; }
}
