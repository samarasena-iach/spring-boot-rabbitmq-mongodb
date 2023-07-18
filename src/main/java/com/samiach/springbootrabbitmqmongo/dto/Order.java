package com.samiach.springbootrabbitmqmongo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("Order")
public class Order {
    @Id
    private String orderId;
    private String itemName;
    private Integer quantity;
    private Double price;
    private String customerId;
    private String retailerName;
    private OrderStatus status;
    private String message;
}
