package com.example.C13_S3_demo.ControllerTest;

import com.example.C13_S3_demo.controller.CustomerController;
import com.example.C13_S3_demo.domain.Customer;
import com.example.C13_S3_demo.domain.Product;
import com.example.C13_S3_demo.exception.CustomerAlreadyExistsException;
import com.example.C13_S3_demo.exception.CustomerNotFoundException;
import com.example.C13_S3_demo.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;


    @Autowired
    MockMvc mockMvc;

    Product product;
    Customer customer;
    List<Customer> customerList;
    List<Customer> list;
    @BeforeEach
    public void setUp(){
        this.product=new Product(1001,"mobile","good product");
        this.customer=new Customer(101,"sam",897654321,product);
        this.customerList=new ArrayList<>();
        this.list=new ArrayList<>();
        this.customerList.add(customer);
        this.list.add(customer);

        this.product=new Product(1002,"laptop","good product");
        this.customer=new Customer(102,"peter",897654321,product);
        this.customerList.add(customer);

        this.product=new Product(1003,"mobile","good product");
        this.customer=new Customer(103,"ruby",897654321,product);
        this.customerList.add(customer);
        this.list.add(customer);

        mockMvc= MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void givenCustomerToSaveReturnCustomerSuccess() throws Exception {
        when(customerService.addCustomer(any())).thenReturn(customer);
        mockMvc.perform(post("/api/v1/customer").
                contentType(MediaType.APPLICATION_JSON).
                content(convertJsonToString(customer))).andExpect(status().isCreated()).
                andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void givenCustomerToSaveReturnCustomerFailure() throws Exception {
        when(customerService.addCustomer(customer)).thenThrow(CustomerAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/customer").
                contentType(MediaType.APPLICATION_JSON).
                content(convertJsonToString(customer))).andExpect(status().isConflict()).
                andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void givenCustomerToDeleteSuccess() throws Exception {
        when(customerService.deleteCustomer(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/customer/102").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void givenCustomerToDeleteFailure() throws Exception {
        when(customerService.deleteCustomer(anyInt())).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(delete("/api/v1/customer/107").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void  givenCustomerReturnAllTheCustomer() throws Exception {
        when(customerService.getAllCustomer()).thenReturn(customerList);
        mockMvc.perform(get("/api/v1/customers").
                contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenProductNameShouldReturnCustomers() throws Exception {
        when(customerService.getAllCustomerByproductName("mobile")).thenReturn(list);
        mockMvc.perform(get("/api/v1/customers/mobile").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }
    public static String convertJsonToString( final Object obj){
        String string;
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            String jsonContent=objectMapper.writeValueAsString(obj);
            string=jsonContent;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            string="json parse error";
        }
        return string;
    }



    @AfterEach
    public void clean(){
        this.product=null;
        this.customer=null;
    }


}
