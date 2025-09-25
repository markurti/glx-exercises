package com.hitachi.mobile.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer_identity")
public class CustomerIdentity {
    @Id
    @Column(name = "unique_id_number", length = 16)
    private String uniqueIdNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "first_name", length = 15)
    private String firstName;

    @Column(name = "last_name", length = 15)
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "state")
    private String state;

    // Constructors
    public CustomerIdentity() {}

    public CustomerIdentity(String uniqueIdNumber, LocalDate dateOfBirth, String firstName, String lastName, String emailAddress, String state) {
        this.uniqueIdNumber = uniqueIdNumber;
        this.dateOfBirth = dateOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.state = state;
    }

    // Getters and Setters
    public String getUniqueIdNumber() { return uniqueIdNumber; }
    public void setUniqueIdNumber(String uniqueIdNumber) { this.uniqueIdNumber = uniqueIdNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
