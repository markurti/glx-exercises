package com.hitachi.mobile.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class IdProofValidationRequest {

    @NotBlank(message = "Aadhar number is required")
    @Pattern(regexp = "\\d{16}", message = "Aadhar number must be 16 digits")
    private String aadharNumber;

    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "First name must be maximum 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name should contain only alphabets")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Last name must be maximum 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name should contain only alphabets")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;

    // Constructors
    public IdProofValidationRequest() {}

    public IdProofValidationRequest(String aadharNumber, String firstName, String lastName, LocalDate dob) {
        this.aadharNumber = aadharNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    // Getters and Setters
    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}