package com.glologistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Customer Entity representing the customer table in PostgreSQL database
 */
@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Integer custId;

    @NotNull
    @Column(name = "cust_name", nullable = false)
    private String custName;

    @NotNull
    @Column(name = "cust_contact", nullable = false)
    private String custContact;

    @NotNull
    @Column(name = "cust_add", nullable = false)
    private String custAdd;

    // One customer can have multiple orders
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    public Customer(Integer custId, String custName, String custContact, String custAdd) {
        this.custId = custId;
        this.custName = custName;
        this.custContact = custContact;
        this.custAdd = custAdd;
    }

    @Override
    public String toString() {
        return "[custId=" + custId + ", custName=" + custName + ", custContact=" + custContact + ", custAdd=" + custAdd + "]";
    }
}