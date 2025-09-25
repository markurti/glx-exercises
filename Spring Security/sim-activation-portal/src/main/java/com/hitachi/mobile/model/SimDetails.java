package com.hitachi.mobile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sim_details")
public class SimDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sim_id")
    private Long simId;

    @Column(name = "service_number", length = 10, nullable = false)
    private String serviceNumber;

    @Column(name = "sim_number", length = 13, nullable = false)
    private String simNumber;

    @Column(name = "sim_status", nullable = false)
    private String simStatus = "inactive";

    // Constructors
    public SimDetails() {}

    public SimDetails(String serviceNumber, String simNumber, String simStatus) {
        this.serviceNumber = serviceNumber;
        this.simNumber = simNumber;
        this.simStatus = simStatus;
    }

    // Getters and Setters
    public Long getSimId() { return simId; }
    public void setSimId(Long simId) { this.simId = simId; }

    public String getServiceNumber() { return serviceNumber; }
    public void setServiceNumber(String serviceNumber) { this.serviceNumber = serviceNumber; }

    public String getSimNumber() { return simNumber; }
    public void setSimNumber(String simNumber) { this.simNumber = simNumber; }

    public String getSimStatus() { return simStatus; }
    public void setSimStatus(String simStatus) { this.simStatus = simStatus; }
}