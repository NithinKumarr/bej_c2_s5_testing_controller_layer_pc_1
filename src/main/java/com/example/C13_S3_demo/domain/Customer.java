package com.example.C13_S3_demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Customer {
    @Id
    private int customerId;
    private String customerName;
    private long phoneNo;
    private Product product;

    public Customer() {
    }

    public Customer(int customerId, String customerName, long phoneNo, Product address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.product = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product address) {
        this.product = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", phoneNo=" + phoneNo +
                ", product=" + product +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId && phoneNo == customer.phoneNo && Objects.equals(customerName, customer.customerName) && Objects.equals(product, customer.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, phoneNo, product);
    }
}
