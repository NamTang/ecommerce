package com.example.ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Role;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.RoleRepository;

@SpringBootApplication
public class EcommerceApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EcommerceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);

    }

    @Bean
    public CommandLineRunner demoData(CustomerRepository customerRepository, RoleRepository roleRepository) {
        return args -> { 

            Customer customer = new Customer();
            customer.setCustomerId(1L);
            customer.setEmail("kalabobobanana@gmail.com");
            customer.setPassword(new BCryptPasswordEncoder().encode("123123"));
            customer.setCustomerName("admin");
            customer = customerRepository.save(customer);

            Role role = new Role();
            role.setAuthority("ROLE_ADMIN");
            role.setCustomer(customer);
            role.setEmail(customer.getEmail());

            roleRepository.save(role);
        };
    }

}
