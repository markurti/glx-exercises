package com.hitachi.mobile.dto;

import java.time.LocalDate;

public class IdProofValidationRequest {
    private String uniqueIdNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public IdProofValidationRequest() {}

    public IdProofValidationRequest(String uniqueIdNumber, String firstName, String lastName, LocalDate dateOfBirth) {
        this.uniqueIdNumber = uniqueIdNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUniqueIdNumber() { return uniqueIdNumber; }
    public void setUniqueIdNumber(String uniqueIdNumber) { this.uniqueIdNumber = uniqueIdNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
}
