package com.example.ecommerce.service;

import com.example.ecommerce.model.Customer;

public interface EmailSenderService {

    void sendActiveCode(Customer customer);

    void sendResetPasswordCode(Customer customer);
}
