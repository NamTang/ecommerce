package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.CustomerOrderShippingAddress;

@Repository
public interface CustomerOrderShippingAddressRepository extends CrudRepository<CustomerOrderShippingAddress, Long> {

}
