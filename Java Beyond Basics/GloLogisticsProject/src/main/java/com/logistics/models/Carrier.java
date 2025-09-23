package com.logistics.models;

public class Carrier {
    private Long id;
    private String name;
    private Contact contactInfo;
    private Rates rates;

    public Carrier(String name, Contact contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public Rates getShippingRates() {
        return rates;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Contact getContactInfo() { return contactInfo; }
    public void setContactInfo(Contact contactInfo) { this.contactInfo = contactInfo; }

    public Rates getRates() { return rates; }
    public void setRates(Rates rates) { this.rates = rates; }

    @Override
    public String toString() {
        return name + " - " + contactInfo;
    }
}
