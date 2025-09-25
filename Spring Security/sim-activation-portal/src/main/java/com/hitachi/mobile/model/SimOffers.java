package com.hitachi.mobile.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sim_offers")
public class SimOffers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "call_qty")
    private Integer callQty;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "data_qty")
    private Integer dataQty;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "offer_name")
    private String offerName;

    @Column(name = "sim_id")
    private Long simId;

    // Constructors
    public SimOffers() {}

    public SimOffers(Integer callQty, Integer cost, Integer dataQty, Integer duration, String offerName, Long simId) {
        this.callQty = callQty;
        this.cost = cost;
        this.dataQty = dataQty;
        this.duration = duration;
        this.offerName = offerName;
        this.simId = simId;
    }

    // Getters and Setters
    public Long getOfferId() { return offerId; }
    public void setOfferId(Long offerId) { this.offerId = offerId; }

    public Integer getCallQty() { return callQty; }
    public void setCallQty(Integer callQty) { this.callQty = callQty; }

    public Integer getCost() { return cost; }
    public void setCost(Integer cost) { this.cost = cost; }

    public Integer getDataQty() { return dataQty; }
    public void setDataQty(Integer dataQty) { this.dataQty = dataQty; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public String getOfferName() { return offerName; }
    public void setOfferName(String offerName) { this.offerName = offerName; }

    public Long getSimId() { return simId; }
    public void setSimId(Long simId) { this.simId = simId; }
}
