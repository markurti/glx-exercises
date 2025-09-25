package com.hitachi.mobile.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class CustomerBasicDetailsRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    // Constructors
    public CustomerBasicDetailsRequest() {}

    public CustomerBasicDetailsRequest(String email, LocalDate dob) {
        this.email = email;
        this.dob = dob;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}
