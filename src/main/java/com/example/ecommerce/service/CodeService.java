package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.model.Code;
import com.example.ecommerce.model.Customer;

public interface CodeService {

    List<Code> findByCodeTypeAndCustomer(int codeType, Customer customer);

    void save(Code code);

    Code findByCodeStr(String codeStr);

    void delete(Code code);
}
