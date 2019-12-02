package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductComment;

@Repository
public interface ProductCommentRepository extends CrudRepository<ProductComment, Long> {
    List<ProductComment> findByProduct(Product product);
}
