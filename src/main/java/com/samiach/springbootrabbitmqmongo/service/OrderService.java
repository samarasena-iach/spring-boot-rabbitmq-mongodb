package com.samiach.springbootrabbitmqmongo.service;

import com.samiach.springbootrabbitmqmongo.dto.Order;

public interface OrderService {

    void orderUpdateMessageFromQueue(Order order);

    String createOrder(Order order);

    void updateOrder(Order order);

    String getOrderStatusFromQueue();

}

