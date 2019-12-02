package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Code;
import com.example.ecommerce.model.Customer;

@Repository
public interface CodeRepository extends CrudRepository<Code, Long> {

    List<Code> findByCodeTypeAndCustomer(int codeType, Customer customer);

    Code findByCodeStr(String codeStr);
}
