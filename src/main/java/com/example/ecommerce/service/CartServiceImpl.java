package com.example.ecommerce.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CustomerOrderService customerOrderService;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartByCartId(Long cartId) {
        Optional<Cart> c = cartRepository.findById(cartId);
        if (c.isPresent()) {
            return c.get();
        }

        return null;
    }

    public void update(Cart cart) {
        double grandTotal = customerOrderService.getCustomerOrderGrandTotalByCart(cart);
        Double truncatedDouble = BigDecimal.valueOf(grandTotal).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        cart.setGrandTotal(truncatedDouble);

        cartRepository.save(cart);
    }

    public Cart validate(Object cartId) throws IOException {
        if (cartId == null) {
            throw new IOException("Please Login.");
        }

        Cart cart = getCartByCartId((Long) cartId);
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new IOException("cart null or cartItem size == 0.c");
        }
        update(cart);

        return cart;
    }

    public void emptyCart(Cart cart) {
        for (CartItem cartItem : cart.getCartItems()) {
            cartItemRepository.delete(cartItem);
        }
        cart.setGrandTotal(0);
        cartRepository.save(cart);
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

}
