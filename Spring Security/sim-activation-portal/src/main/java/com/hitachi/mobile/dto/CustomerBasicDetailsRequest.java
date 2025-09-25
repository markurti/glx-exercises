package com.hitachi.mobile.dto;

import java.time.LocalDate;

public class CustomerBasicDetailsRequest {
    private String email;
    private LocalDate dob;

    public CustomerBasicDetailsRequest() {}

    public CustomerBasicDetailsRequest(String email, LocalDate dob) {
        this.email = email;
        this.dob = dob;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}
