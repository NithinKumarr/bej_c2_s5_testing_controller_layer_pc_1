package com.example.C13_S3_demo.service;

import com.example.C13_S3_demo.domain.Customer;
import com.example.C13_S3_demo.exception.CustomerAlreadyExistsException;
import com.example.C13_S3_demo.exception.CustomerNotFoundException;
import com.example.C13_S3_demo.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
ICustomerRepository customerRepository;

@Autowired
public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) throws CustomerAlreadyExistsException {
    if(customerRepository.findById(customer.getCustomerId()).isEmpty()){
        return customerRepository.save(customer);
    }
    throw new CustomerAlreadyExistsException();
}

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomer(Integer customerId) throws CustomerNotFoundException {
       if(customerRepository.findById(customerId).isEmpty()){
           throw new CustomerNotFoundException();
       }
       customerRepository.deleteById(customerId);
       return true;
    }
    @Override
    public List<Customer> getAllCustomerByproductName(String productName) {
        return customerRepository.findTheCustomerByproductName(productName);
    }
}
