package com.hitachi.mobile.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "unique_id_number", length = 16)
    private String uniqueIdNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "first_name", length = 15)
    private String firstName;

    @Column(name = "last_name", length = 15)
    private String lastName;

    @Column(name = "id_type")
    private String idType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_address_id")
    private CustomerAddress customerAddress;

    @Column(name = "sim_id")
    private Long simId;

    @Column(name = "state")
    private String state;

    // Constructors
    public Customer() {}

    // Getters and Setters
    public String getUniqueIdNumber() { return uniqueIdNumber; }
    public void setUniqueIdNumber(String uniqueIdNumber) { this.uniqueIdNumber = uniqueIdNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getIdType() { return idType; }
    public void setIdType(String idType) { this.idType = idType; }

    public CustomerAddress getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(CustomerAddress customerAddress) { this.customerAddress = customerAddress; }

    public Long getSimId() { return simId; }
    public void setSimId(Long simId) { this.simId = simId; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
