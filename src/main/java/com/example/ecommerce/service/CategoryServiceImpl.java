package com.example.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepostitory;

    @Override
    public List<String> getAllSubCategory() {
        return categoryRepostitory.findAllSubCategoryName();
    }

    @Override
    public List<String> getAllMainCategory() {
        return categoryRepostitory.findAllMainCategoryName();
    }

    @Override
    public List<Category> getAllCategory() {
        return (List<Category>) categoryRepostitory.findAll();
    }

    @Override
    public void save(Category category) {
        categoryRepostitory.save(category);
    }

    @Override
    public void delete(Long categoryId) {
        categoryRepostitory.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> cat = categoryRepostitory.findById(categoryId);
        if (cat.isPresent()) {
            return cat.get();
        }

        return null;
    }
}
