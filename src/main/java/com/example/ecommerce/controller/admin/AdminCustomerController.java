package com.example.ecommerce.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.service.CustomerService;

@Controller
@RequestMapping("/admin/cu")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/m")
    public String customerManagement(Model model) {
        List<Customer> customerList = customerService.getAllCustomer();
        model.addAttribute("customerList", customerList);

        return "admin/customerManagement";
    }

    @GetMapping("/d")
    public String removeCustomer(@RequestParam(value = "id", required = true) Long customerId) {
        customerService.delete(customerId);
        return "redirect:/admin/cu/m";
    }

    @GetMapping("/e")
    public String enableCustomer(@RequestParam(value = "id", required = true) Long customerId) {
        Customer customer = customerService.findById(customerId);
        customer.setEnabled(true);
        customerService.save(customer);
        return "redirect:/admin/cu/m";
    }

    @GetMapping("/ds")
    public String disableCustomer(@RequestParam(value = "id", required = true) Long customerId) {
        Customer customer = customerService.findById(customerId);
        customer.setEnabled(false);
        customerService.save(customer);
        return "redirect:/admin/cu/m";
    }
}
