package com.hitachi.mobile.dto;

import jakarta.validation.constraints.*;

public class CustomerPersonalDetailsRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 15, message = "First name must be maximum 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name should contain only alphabets")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 15, message = "Last name must be maximum 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name should contain only alphabets")
    private String lastName;

    @NotBlank(message = "Confirm email is required")
    @Email(message = "Invalid email format")
    private String confirmEmail;

    // Constructors
    public CustomerPersonalDetailsRequest() {}

    public CustomerPersonalDetailsRequest(String firstName, String lastName, String confirmEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.confirmEmail = confirmEmail;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getConfirmEmail() { return confirmEmail; }
    public void setConfirmEmail(String confirmEmail) { this.confirmEmail = confirmEmail; }
}

