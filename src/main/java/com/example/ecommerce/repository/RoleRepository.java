package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByAuthorityAndCustomer(String auth, Customer customer);
}
