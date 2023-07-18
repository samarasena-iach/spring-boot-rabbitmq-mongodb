package com.samiach.springbootrabbitmqmongo.repository;

import com.samiach.springbootrabbitmqmongo.dto.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
