package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.ShippingAddress;

@Repository
public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long> {

    List<ShippingAddress> findAllByCustomer(Customer customer);

}
