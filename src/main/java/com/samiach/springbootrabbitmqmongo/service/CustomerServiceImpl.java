package com.samiach.springbootrabbitmqmongo.service;

import com.samiach.springbootrabbitmqmongo.config.MessagingConfiguration;
import com.samiach.springbootrabbitmqmongo.dto.Customer;
import com.samiach.springbootrabbitmqmongo.dto.Order;
import com.samiach.springbootrabbitmqmongo.dto.OrderStatus;
import com.samiach.springbootrabbitmqmongo.repository.CustomerRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RabbitListener(queues = MessagingConfiguration.ORDER_QUEUE)
    @Override
    public void customerMessageFromQueue(Order order) {
        Order receivedOrder = order;
        System.out.println("#### receivedOrder $$$$ " + receivedOrder);
        purchaseOperation(receivedOrder);
    }

    @Override
    public void purchaseOperation(Order order) {
        Customer dbCustomer = customerRepository.findById(order.getCustomerId()).get();
        validateAndInitiatePayment(order, dbCustomer);
        rabbitTemplate.convertAndSend(MessagingConfiguration.CONSUMER_QUEUE, order);
    }

    private void validateAndInitiatePayment(Order order, Customer dbCustomer) {
        Boolean flag = order.getPrice() > dbCustomer.getBalance() ? Boolean.FALSE : Boolean.TRUE;

        if (!flag) {
            System.out.println("!!!!!!!! DECLINED !!!!!!!!" + order);
            order.setStatus(OrderStatus.DECLINED);
            order.setMessage("!! INSUFFICIENT BALANCE, PLEASE TOP-UP WALLET BALANCE BEFORE PURCHASE!!");
        } else {
            System.out.println("!!!!!!!! ACCEPTED !!!!!!!!" + order);
            deductMoney(order.getPrice(), dbCustomer);
            order.setStatus(OrderStatus.ACCEPTED);
            order.setMessage("!! ORDER ACCEPTED !!");
        }
    }

    @Override
    public void create(Customer customer) {
        mongoTemplate.save(customer);
    }

    @Override
    public Customer addMoney(Customer customer) {
        Customer dbCustomer = customerRepository.findById(customer.getCustomerId()).get();
        Double amountToBeAdded = dbCustomer.getBalance() + customer.getBalance();
        dbCustomer.setBalance(amountToBeAdded);
        mongoTemplate.save(dbCustomer);
        return dbCustomer;
    }

    @Override
    public Customer deductMoney(Double payment, Customer customer) {
        Double amountAfterPurchase = customer.getBalance() - payment;
        customer.setBalance(amountAfterPurchase);
        mongoTemplate.save(customer);
        return customer;
    }
}
