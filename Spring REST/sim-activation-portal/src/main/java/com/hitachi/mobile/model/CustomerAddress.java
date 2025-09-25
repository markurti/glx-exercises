package com.hitachi.mobile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_address")
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "address", length = 25)
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code", length = 6)
    private String pinCode;

    // Constructors
    public CustomerAddress() {}

    public CustomerAddress(String address, String city, String state, String pinCode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    // Getters and Setters
    public Long getAddressId() { return addressId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
}