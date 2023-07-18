package com.samiach.springbootrabbitmqmongo.service;

import com.samiach.springbootrabbitmqmongo.config.MessagingConfiguration;
import com.samiach.springbootrabbitmqmongo.dto.Order;
import com.samiach.springbootrabbitmqmongo.dto.OrderStatus;
import com.samiach.springbootrabbitmqmongo.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_VERIFICATION_IN_PRGRESS = "Order Verification In Progress";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private Order receivedOrderFromQueue;

    @RabbitListener(queues = MessagingConfiguration.CONSUMER_QUEUE)
    @Override
    public void orderUpdateMessageFromQueue(Order order) {
        receivedOrderFromQueue = order;
        System.out.println("#### receivedOrderFromQueue $$$$ " + receivedOrderFromQueue);
        updateOrder(order);
    }

    @Override
    public String createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setMessage(ORDER_VERIFICATION_IN_PRGRESS);
        orderRepository.save(order);
        rabbitTemplate.convertAndSend(MessagingConfiguration.ORDER_QUEUE, order);
        return order.getMessage();
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public String getOrderStatusFromQueue() {
        return receivedOrderFromQueue.getStatus() + "!! Message !!" + receivedOrderFromQueue.getMessage();
    }
}
