package com.samiach.springbootrabbitmqmongo.service;

import com.samiach.springbootrabbitmqmongo.dto.Customer;
import com.samiach.springbootrabbitmqmongo.dto.Order;

public interface CustomerService {

    void customerMessageFromQueue(Order order);

    void purchaseOperation(Order order);

    void create(Customer customer);

    Customer addMoney(Customer customer);

    Customer deductMoney(Double payment, Customer customer);

}
