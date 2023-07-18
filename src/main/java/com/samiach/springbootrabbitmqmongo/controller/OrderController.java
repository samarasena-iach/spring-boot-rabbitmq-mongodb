package com.samiach.springbootrabbitmqmongo.controller;

import com.samiach.springbootrabbitmqmongo.dto.Order;
import com.samiach.springbootrabbitmqmongo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        return orderService.createOrder(order);
    }

    @GetMapping("/orderStatus")
    public String checkStatus() {
        return orderService.getOrderStatusFromQueue();
    }

}
