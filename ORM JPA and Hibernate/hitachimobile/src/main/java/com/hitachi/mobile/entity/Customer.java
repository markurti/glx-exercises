package com.hitachi.mobile.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "unique_id_number")
    private Long uniqueIdNumber;

    @Column(name = "date_of_birth", length = 20, nullable = false)
    private String dateOfBirth;

    @Column(name = "email_address", length = 50, nullable = false)
    private String emailAddress;

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "id_type", length = 20, nullable = false)
    private String idType;

    @Column(name = "city", length = 20, nullable = false)
    private String city;

    @Column(name = "state", length = 20, nullable = false)
    private String state;

    // Constructors
    public Customer() {}

    public Customer(Long uniqueIdNumber, String dateOfBirth, String emailAddress,
                    String firstName, String lastName, String idType, String city, String state) {
        this.uniqueIdNumber = uniqueIdNumber;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idType = idType;
        this.city = city;
        this.state = state;
    }

    // Getters and Setters
    public Long getUniqueIdNumber() { return uniqueIdNumber; }
    public void setUniqueIdNumber(Long uniqueIdNumber) { this.uniqueIdNumber = uniqueIdNumber; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getIdType() { return idType; }
    public void setIdType(String idType) { this.idType = idType; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    @Override
    public String toString() {
        return String.format("Customer{id=%d, name='%s %s', email='%s', city='%s', state='%s'}",
                uniqueIdNumber, firstName, lastName, emailAddress, city, state);
    }
}