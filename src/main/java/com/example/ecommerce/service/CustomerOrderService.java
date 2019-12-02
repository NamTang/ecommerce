package com.example.ecommerce.service;

import java.io.IOException;
import java.util.List;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.CustomerOrder;
import com.example.ecommerce.model.CustomerOrderShippingAddress;

public interface CustomerOrderService {

    double getCustomerOrderGrandTotalByCart(Cart cart);

    void addOrderDumpCart(CustomerOrderShippingAddress customerOrderShippingAddress, CustomerOrder customerOrder, Cart cart) throws IOException;

    List<CustomerOrder> getAllCustomerOrderByCustomer(Customer customer);
}
