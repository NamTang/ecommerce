package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.model.Customer;

public interface CustomerService {
	
	Customer findById(Long customerId);
	
	void save(Customer customer);
	
	void activeAccount(String codeStr);
	
	Customer findByEmail(String email);
	
	boolean hasRole(String role, Customer customer);
	
	List<Customer> getAllCustomer();
	
	void delete(Long customerId);
}
