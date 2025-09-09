package org.example;

public class Customer {
    private int custId;
    private String customerName;
    private String contactNumber;
    private String address;

    public Customer() {}

    public Customer(int custId, String customerName, String contactNumber, String address) {
        this.custId = custId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    // Getters and Setters
    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer [custId=" + custId +
                ", customerName=" + customerName +
                ", contactNumber=" + contactNumber +
                ", address=" + address + "]";
    }
}
