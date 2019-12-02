package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Slider;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.SliderRepository;
import com.example.ecommerce.service.CategoryService;

@Controller
public class MainController {

    @Autowired
    private SliderRepository sliderRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductRepository productRepository;

    /*
     * Home.jsp
     */
    @GetMapping(value = "/")
    public String home(Model model) {

        List<Slider> sliderList = (List<Slider>) sliderRepository.findAll();
        List<Product> productList = productRepository.findAll(PageRequest.of(0, 16, Sort.unsorted())).getContent();
        List<String> mainCategoryNameList = categoryService.getAllMainCategory();
        List<Product> productPopularList = productRepository.findAll(PageRequest.of(0, 8, Direction.DESC, "productViews")).getContent();
        List<Product> productLatest = productRepository.findAll(PageRequest.of(0, 8, Direction.DESC, "productDate")).getContent();

        model.addAttribute("productPopular", productPopularList);
        model.addAttribute("productLatest", productLatest);
        model.addAttribute("sliders", sliderList);
        model.addAttribute("products", productList);
        model.addAttribute("mainCategoryNameList", mainCategoryNameList);
        return "home";
    }

    @GetMapping("/version")
    public String version() {
        return "version";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
