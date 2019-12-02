package com.example.ecommerce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.CustomerOrder;
import com.example.ecommerce.model.ShippingAddress;
import com.example.ecommerce.service.CustomerAddressService;
import com.example.ecommerce.service.CustomerOrderService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.EmailSenderService;

@Controller
@RequestMapping("/customer/account")
public class UserAccountController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerAddressService customerAddressService;
    @Autowired
    private CustomerOrderService customerOrderService;
    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping
    public String account(HttpServletRequest request, Model model) {

        Long customerId = (Long) request.getSession().getAttribute("customerId_");
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        List<ShippingAddress> customerShippingAddresses = customerAddressService.getAllShippingAddressByCustomerId(customerId);
        for (ShippingAddress defaultShippingAddress : customerShippingAddresses) {
            if (defaultShippingAddress.getIsDefault()) {
                model.addAttribute("shippingAddress", defaultShippingAddress);
                break;
            }
        }

        if (customerService.hasRole("ROLE_UNAUTH", customer)) {
            model.addAttribute("unAuth", "unAuth");
        }

        // Get Customer Orders
        List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrderByCustomer(customer);
        model.addAttribute("customerOrders", customerOrders);

        return "myAccount";
    }

    @GetMapping(value = "/rsac")
    public void resendActiveCode(HttpServletRequest request) {
        Long customerId = (Long) request.getSession().getAttribute("customerId_");
        Customer customer = customerService.findById(customerId);
        if (customer != null && !customerService.hasRole("ROLE_USER", customer)) {
            emailSenderService.sendActiveCode(customer);
        }
    }
}
