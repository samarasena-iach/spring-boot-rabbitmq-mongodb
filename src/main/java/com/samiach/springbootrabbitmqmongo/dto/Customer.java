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
@Document("Customer")
public class Customer {
    @Id
    private String customerId;
    private String name;
    private Double balance;
}
