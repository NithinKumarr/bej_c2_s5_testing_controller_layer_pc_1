package com.example.C13_S3_demo.service;

import com.example.C13_S3_demo.domain.Customer;
import com.example.C13_S3_demo.exception.CustomerAlreadyExistsException;
import com.example.C13_S3_demo.exception.CustomerNotFoundException;

import java.util.List;

public interface ICustomerService {
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException;

    public List<Customer> getAllCustomer();

    public boolean deleteCustomer(Integer customerId) throws CustomerNotFoundException;

    public List<Customer> getAllCustomerByproductName(String city);
}
