package com.example.ecommerce.service;

import java.io.IOException;

import com.example.ecommerce.model.Cart;

public interface CartService {

    Cart validate(Object customerId) throws IOException;

    void emptyCart(Cart cart);

    void save(Cart cart);
}
