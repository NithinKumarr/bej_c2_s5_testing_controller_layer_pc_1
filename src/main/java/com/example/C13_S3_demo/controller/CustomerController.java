package com.example.C13_S3_demo.controller;

import com.example.C13_S3_demo.domain.Customer;
import com.example.C13_S3_demo.exception.CustomerAlreadyExistsException;
import com.example.C13_S3_demo.exception.CustomerNotFoundException;
import com.example.C13_S3_demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }
    @DeleteMapping("customer/{id}")
    public ResponseEntity<?> deleteCustomers(@PathVariable Integer id) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }
    @GetMapping("/customers/{name}")
    public ResponseEntity<?> getAllTheCustomersByCity(@PathVariable String name){
        return new ResponseEntity<>(customerService.getAllCustomerByproductName(name), HttpStatus.OK);
    }
}
