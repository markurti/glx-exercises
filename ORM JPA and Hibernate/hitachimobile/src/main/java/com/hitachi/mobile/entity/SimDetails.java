package com.hitachi.mobile.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sim_details")
public class SimDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sim_id")
    private Integer simId;

    @Column(name = "service_number", nullable = false, unique = true)
    private Long serviceNumber;

    @Column(name = "sim_number", nullable = false, unique = true)
    private Long simNumber;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "inactive";

    // Constructors
    public SimDetails() {}

    public SimDetails(Long serviceNumber, Long simNumber, String status) {
        this.serviceNumber = serviceNumber;
        this.simNumber = simNumber;
        this.status = status;
    }

    // Getters and Setters
    public Integer getSimId() { return simId; }
    public void setSimId(Integer simId) { this.simId = simId; }

    public Long getServiceNumber() { return serviceNumber; }
    public void setServiceNumber(Long serviceNumber) { this.serviceNumber = serviceNumber; }

    public Long getSimNumber() { return simNumber; }
    public void setSimNumber(Long simNumber) { this.simNumber = simNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("SimDetails{simId=%d, serviceNumber=%d, simNumber=%d, status='%s'}",
                simId, serviceNumber, simNumber, status);
    }
}
