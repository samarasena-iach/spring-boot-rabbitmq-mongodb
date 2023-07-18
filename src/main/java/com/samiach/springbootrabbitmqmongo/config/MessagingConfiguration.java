package com.samiach.springbootrabbitmqmongo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {

    public static final String ORDER_QUEUE = "order_queue";
    public static final String CONSUMER_QUEUE = "consumer_queue";
    public static final String ORDER_TOPIC = "order_topic";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE);
    }

    @Bean
    public Queue consumerQueue() {
        return new Queue(CONSUMER_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(ORDER_TOPIC);
    }

    @Bean
    public Binding orderBinding(TopicExchange exchange) {
        return BindingBuilder.bind(orderQueue()).to(exchange).with(orderQueue().getName());
    }

    @Bean
    public Binding consumerBinding(TopicExchange exchange) {
        return BindingBuilder.bind(consumerQueue()).to(exchange).with(consumerQueue().getName());
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
