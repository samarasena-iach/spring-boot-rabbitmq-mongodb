package com.samiach.springbootrabbitmqmongo.repository;

import com.samiach.springbootrabbitmqmongo.dto.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
