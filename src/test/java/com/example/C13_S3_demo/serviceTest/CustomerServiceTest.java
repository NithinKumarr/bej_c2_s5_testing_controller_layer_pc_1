package com.example.C13_S3_demo.serviceTest;

import com.example.C13_S3_demo.domain.Customer;
import com.example.C13_S3_demo.domain.Product;
import com.example.C13_S3_demo.exception.CustomerAlreadyExistsException;
import com.example.C13_S3_demo.exception.CustomerNotFoundException;
import com.example.C13_S3_demo.repository.ICustomerRepository;
import com.example.C13_S3_demo.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    ICustomerRepository customerRepository;
    @InjectMocks
    CustomerService customerService;

    private Customer customer;
    private Customer customer1;
    private Customer customer2;
    private Product product;
    private Product product1;
    private Product product2;
    private List<Customer> customerList;
    private List<Customer> list;

    @BeforeEach
    public void setUp(){
        customerList=new ArrayList<>();
        list=new ArrayList<>();

        this.product=new Product(1002,"mobile","good product");
        this.customer=new Customer(102,"sam",897654321,product);
        customerList.add(customer);
        list.add(customer);

        this.product1=new Product(1003,"laptop","good product");
        this.customer1=new Customer(103,"peter",897654321,product1);
        customerList.add(customer1);

        this.product2=new Product(1004,"mobile","good product");
        this.customer2=new Customer(104,"ruby",897654321,product2);
        list.add(customer2);
        customerList.add(customer2);

    }
    @Test
    public void GivenCustomerToSaveShouldReturnCustomerSuccess() throws CustomerAlreadyExistsException {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(null));
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer,customerService.addCustomer(customer));
        verify(customerRepository,times(1)).save(customer);
    }
    @Test
    public void GivenCustomerToSaveShouldReturnCustomerFailure() {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(customer));
        assertThrows(CustomerAlreadyExistsException.class,()->customerService.addCustomer(customer));
        verify(customerRepository,times(1)).findById(customer.getCustomerId());
    }
    @Test
    public void GivenCustomerIdDeleteCustomerSuccess() throws CustomerNotFoundException {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(customer));
        boolean result=customerService.deleteCustomer(customer.getCustomerId());
        assertEquals(true,result);
    }
    @Test
    public void GivenCustomerIdDeleteCustomerFailure() throws CustomerNotFoundException {
        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.ofNullable(null));
        assertThrows(CustomerNotFoundException.class,()->customerService.deleteCustomer(customer.getCustomerId()));
    }
    @Test
    public void GivenCustomersGetAllTheCustomersSuccess(){
        when(customerRepository.findAll()).thenReturn(customerList);
        assertEquals(3,customerService.getAllCustomer().size());
    }
    @Test
    public void GivenCustomersGetAllTheCustomersFailure(){
        when(customerRepository.findAll()).thenReturn(customerList);
        assertNotEquals(2,customerService.getAllCustomer().size());
    }

    @Test
    public void givenProductNameReturnCustomers(){
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> customerList1=customerService.getAllCustomer();
        List<Customer> newList=new ArrayList<>();
        Iterator<Customer> iterator=customerList1.iterator();
        while (iterator.hasNext()){
            Customer newCustomer=iterator.next();
            if (newCustomer.getProduct().getProductName().equals("mobile")){
                newList.add(newCustomer);
            }
        }

        when(customerRepository.findTheCustomerByproductName(customer.getProduct().getProductName())).thenReturn(list);
        List<Customer> list1=new ArrayList<>();
        list1=customerService.getAllCustomerByproductName("mobile");

        assertEquals(newList,list1);

    }
    @AfterEach
    public void clean(){
        this.product=null;
        this.customer=null;
    }
}
