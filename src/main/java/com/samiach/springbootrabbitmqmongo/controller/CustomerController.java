package com.samiach.springbootrabbitmqmongo.controller;

import com.samiach.springbootrabbitmqmongo.dto.Customer;
import com.samiach.springbootrabbitmqmongo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        customer.setCustomerId(UUID.randomUUID().toString());
        customerService.create(customer);
        return customer;
    }

    @PutMapping("/addMoney")
    public Customer addMoney(@RequestBody Customer customer) {
        return customerService.addMoney(customer);
    }

}
