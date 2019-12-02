package com.example.ecommerce.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.CustomerOrder;
import com.example.ecommerce.model.CustomerOrderItem;
import com.example.ecommerce.model.CustomerOrderShippingAddress;
import com.example.ecommerce.model.ShippingAddress;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CustomerOrderItemRepository;
import com.example.ecommerce.repository.CustomerOrderRepository;
import com.example.ecommerce.repository.CustomerOrderShippingAddressRepository;
import com.example.ecommerce.repository.ShippingAddressRepository;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CartService cartService;
    @Autowired
    private CustomerOrderRepository customerOrderRepository;
    @Autowired
    private CustomerOrderShippingAddressRepository customerOrderShippingAddressRepository;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private CustomerOrderItemRepository customerOrderItemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public double getCustomerOrderGrandTotalByCart(Cart cart) {
        double grandTotal = 0;
        Set<CartItem> cartItems = cart.getCartItems();

        for (CartItem item : cartItems) {
            grandTotal += item.getTotalPriceDouble();
        }

        return grandTotal;
    }

    public void addOrderDumpCart(CustomerOrderShippingAddress customerOrderShippingAddress, CustomerOrder customerOrder, Cart cart)
            throws IOException {

        if (customerOrderShippingAddress == null || customerOrder == null || cart == null) {
            throw new IOException();
        }
        // initiate customerOrderShippingAddress
        Optional<ShippingAddress> sa = shippingAddressRepository.findById(customerOrderShippingAddress.getOriginalShippingAddressId());
        if (!sa.isPresent()) {
            throw new IOException();
        }

        ShippingAddress shippingAddress = sa.get();
        customerOrderShippingAddress.setAddress(shippingAddress.getAddress());
        customerOrderShippingAddress.setCity(shippingAddress.getCity());
        customerOrderShippingAddress.setCountry(shippingAddress.getCountry());
        customerOrderShippingAddress.setFullName(shippingAddress.getFullName());
        customerOrderShippingAddress.setPhoneNumber(shippingAddress.getPhoneNumber());
        customerOrderShippingAddress.setState(shippingAddress.getState());
        customerOrderShippingAddress.setZipCode(shippingAddress.getZipCode());

        // initiate customer order
        customerOrder.setCustomer(cart.getCustomer());
        customerOrder.setOrderDate(new Date());
        customerOrder.setOrderTotalPrice(cart.getGrandTotal());
        // for mapping orderItem table
        customerOrderRepository.save(customerOrder);
        cart.setGrandTotal(0);
        cartService.save(cart);
        // dump cartItem to orderItem, empty cart
        for (CartItem cartItem : cart.getCartItems()) {
            CustomerOrderItem customerOrderItem = new CustomerOrderItem();
            customerOrderItem.setCustomerOrder(customerOrder);
            customerOrderItem.setProductId(cartItem.getProduct().getProductId());
            customerOrderItem.setProductName(cartItem.getProduct().getProductName());
            customerOrderItem.setProductPrice(cartItem.getProduct().getProductPrice());
            customerOrderItem.setProductQuantity(cartItem.getQuantity());
            customerOrderItemRepository.save(customerOrderItem);
            cartItemRepository.delete(cartItem);
        }
        // for mapping customerOrder table
        customerOrderShippingAddressRepository.save(customerOrderShippingAddress);

        customerOrder.setCustomerOrderShippingAddress(customerOrderShippingAddress);
        customerOrderRepository.save(customerOrder);

        customerOrderShippingAddress.setCustomerOrder(customerOrder);
        customerOrderShippingAddressRepository.save(customerOrderShippingAddress);
    }

    public List<CustomerOrder> getAllCustomerOrderByCustomer(Customer customer) {
        return customerOrderRepository.findAllByCustomer(customer);
    }
}
