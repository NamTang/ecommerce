package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.model.ShippingAddress;

public interface CustomerAddressService {

    List<ShippingAddress> getAllShippingAddressByCustomerId(Object customerId);

    void addShippingAddressObject(Object customerId, ShippingAddress shippingAddress);
}
