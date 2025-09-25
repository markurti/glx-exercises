package com.hitachi.mobile.dto;

public class CustomerPersonalDetailsRequest {
    private String firstName;
    private String lastName;
    private String confirmEmail;

    public CustomerPersonalDetailsRequest() {}

    public CustomerPersonalDetailsRequest(String firstName, String lastName, String confirmEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.confirmEmail = confirmEmail;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getConfirmEmail() { return confirmEmail; }
    public void setConfirmEmail(String confirmEmail) { this.confirmEmail = confirmEmail; }
}
