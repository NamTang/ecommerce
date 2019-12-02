package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.CartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long>{

}
