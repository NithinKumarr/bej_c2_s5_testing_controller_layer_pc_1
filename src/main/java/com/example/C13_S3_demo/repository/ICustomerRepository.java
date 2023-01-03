package com.example.C13_S3_demo.repository;

import com.example.C13_S3_demo.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerRepository extends MongoRepository<Customer,Integer> {
    @Query("{'product.productName':{$in:[?0]}}")
    public List<Customer> findTheCustomerByproductName(String productName);
}
