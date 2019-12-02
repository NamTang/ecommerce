package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByEmail(String email);

}
