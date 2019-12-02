package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {

    List<CustomerOrder> findAllByCustomer(Customer customer);
}
