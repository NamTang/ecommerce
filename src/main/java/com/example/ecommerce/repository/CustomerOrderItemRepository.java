package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.CustomerOrderItem;

@Repository
public interface CustomerOrderItemRepository extends CrudRepository<CustomerOrderItem, Long> {

}
