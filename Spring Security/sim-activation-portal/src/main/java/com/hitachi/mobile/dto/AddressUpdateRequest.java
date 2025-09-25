package com.hitachi.mobile.dto;

import jakarta.validation.constraints.*;

public class AddressUpdateRequest {

    @NotBlank(message = "Unique ID is required")
    private String uniqueIdNumber;

    @Size(max = 25, message = "Address should be maximum 25 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "City should not contain special characters except space")
    private String city;

    @NotBlank(message = "State is required")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "State should not contain special characters except space")
    private String state;

    @NotBlank(message = "PIN code is required")
    @Pattern(regexp = "\\d{6}", message = "PIN code should be 6 digits")
    private String pinCode;

    // Constructors
    public AddressUpdateRequest() {}

    public AddressUpdateRequest(String uniqueIdNumber, String address, String city, String state, String pinCode) {
        this.uniqueIdNumber = uniqueIdNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    // Getters and Setters
    public String getUniqueIdNumber() { return uniqueIdNumber; }
    public void setUniqueIdNumber(String uniqueIdNumber) { this.uniqueIdNumber = uniqueIdNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
}
