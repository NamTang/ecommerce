package com.example.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.model.ProductTag;

@Repository
public interface ProductTagRepository extends CrudRepository<ProductTag, Long> {

//	@Query(value = "SELECT DISTINCT tag_contents from product_tag" , nativeQuery = true)
//	List<ProductTag> findAllTags();
}
