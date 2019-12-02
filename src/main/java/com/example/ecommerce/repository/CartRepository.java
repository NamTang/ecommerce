package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

}
