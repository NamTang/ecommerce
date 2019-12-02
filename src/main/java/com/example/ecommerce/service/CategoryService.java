package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.model.Category;

public interface CategoryService {
	
	List<Category> getAllCategory();
	
	List<String> getAllMainCategory();

	List<String> getAllSubCategory();
	
	void save(Category category);
	
	void delete(Long categoryId);
	
	Category getCategoryById(Long categoryId);
}
