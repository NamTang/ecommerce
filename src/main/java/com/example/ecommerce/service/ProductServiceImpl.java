package com.example.ecommerce.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public void getVisited(Long productId) {
        Optional<Product> p = productRepository.findById(productId);
        if (p.isPresent()) {
            Product product = p.get();
            product.setProductViews(product.getProductViews() + 1);
            productRepository.save(product);
        }
    }

    @Override
    public List<Product> getProductsByMainCategory(String mainCategoryName) {
        List<Category> categoryList = categoryRepository.findAllByMainCategoryName(mainCategoryName);
        List<Product> products = new ArrayList<Product>();
        for (Category category : categoryList) {
            for (Product product : category.getProducts()) {
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public List<Product> sort(List<Product> products, String sortType) {
        // 0: Price ASC, 1: Price DESC
        if (sortType.equals("0")) {
            Collections.sort(products, Product.Comparators.PRICE);
        }
        if (sortType.equals("1")) {
            Collections.sort(products, Product.Comparators.PRICE);
            Collections.reverse(products);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findAllByProductCategory(category);
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> p = productRepository.findById(productId);
        if (p.isPresent()) {
            return p.get();
        }

        return null;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }
}
