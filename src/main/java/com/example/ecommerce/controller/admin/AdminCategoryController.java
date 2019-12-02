package com.example.ecommerce.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.service.CategoryService;

@Controller
@RequestMapping("/admin/ca")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/m")
    public String categoryManagement(Model model) {
        List<Category> categoryList = categoryService.getAllCategory();
        model.addAttribute("categoryList", categoryList);

        return "admin/categoryManagement";
    }

    @GetMapping(value = "/s")
    public String addCategory(@RequestParam(value = "id", required = false) Long categoryId, Model model) {
        // edit category
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            model.addAttribute("title", "Edit Category");
            model.addAttribute("category", category);
        } else {
            // create category
            Category category = new Category();
            model.addAttribute("title", "Add Category");
            model.addAttribute("category", category);
        }

        return "admin/saveCategory";
    }

    @PostMapping(value = "/s")
    public String addCategoryPost(@Valid @ModelAttribute("category") Category category, BindingResult result) {

        if (result.hasErrors()) {
            return "admin/saveCategory";
        }
        categoryService.save(category);
        return "redirect:/admin/ca/m";
    }

    @GetMapping("/d")
    public String deleteProductById(@RequestParam(value = "id", required = true) Long categoryId) {

        categoryService.delete(categoryId);

        return "redirect:/admin/ca/m";
    }
}
