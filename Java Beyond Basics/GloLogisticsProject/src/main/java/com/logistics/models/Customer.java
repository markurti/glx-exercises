package com.logistics.models;

public class Customer {
    private Long id;
    private String name;
    private Contact contactInfo;

    public Customer(String name, Contact contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Contact getContactInfo() { return contactInfo; }
    public void setContactInfo(Contact contactInfo) { this.contactInfo = contactInfo; }

    @Override
    public String toString() {
        return name + " - " + contactInfo;
    }
}
