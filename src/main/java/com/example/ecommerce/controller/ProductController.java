package com.example.ecommerce.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductComment;
import com.example.ecommerce.model.ProductTag;
import com.example.ecommerce.repository.ProductCommentRepository;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.ProductService;

@Controller
@RequestMapping("/pd")
public class ProductController {

    @Autowired
    private ProductCommentRepository productCommentRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String getProducts(Model model) {
        return "products";
    }

    /*
     * Get all product by mainCategory -> filter by subCategory (option) -> tag
     * (option) -> price (option)
     * 
     * Model Attribute: products(List<Product>), subCategoryList(Set<String>),
     * tagList(Set<String>)
     */
    @GetMapping("/s")
    public String getProductsByKeyword(@RequestParam(value = "na", required = false) String productName,
            @RequestParam(value = "mc", required = false) String mainCategoryName,
            @RequestParam(value = "sc", required = false) String subCategoryName, @RequestParam(value = "st", required = false) String sortType,
            @RequestParam(value = "t", required = false) String tag, @RequestParam(value = "lp", required = false) String lowerPrice,
            @RequestParam(value = "hp", required = false) String higherPrice, Model model) throws IOException {
        // filter initiate
        boolean nameFilter = false;
        boolean mainCategoryFilter = false;
        boolean subCategoryFilter = false;
        boolean needSort = false;
        boolean tagFilter = false;
        boolean priceFilter = false;

        int lowerPrice_i = 0;
        int higherPrice_i = 0;

        /* All the required filter check here */
        if (productName != null) {
            nameFilter = true;
        }
        if (mainCategoryName != null) {
            mainCategoryFilter = true;
        }
        if (subCategoryName != null) {
            subCategoryFilter = true;
        }
        if (sortType != null) {
            needSort = true;
        }
        if (tag != null) {
            tagFilter = true;
        }
        if (lowerPrice != null && higherPrice != null) {
            lowerPrice_i = Integer.parseInt(lowerPrice);
            higherPrice_i = Integer.parseInt(higherPrice);
            priceFilter = true;
        }

        // get all product
        List<Product> products = productService.getAllProducts();

        // Filter by product name
        Iterator<Product> iter = products.iterator();

        if (nameFilter) {
            while (iter.hasNext()) {
                Product product = iter.next();
                if (!product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
                    iter.remove();
                }
            }
        }
        // Filter by product mainCategory
        iter = products.listIterator();
        if (mainCategoryFilter) {
            while (iter.hasNext()) {
                Product product = iter.next();
                Category category = product.getProductCategory();
                if (!category.getMainCategoryName().equalsIgnoreCase(mainCategoryName)) {
                    iter.remove();
                }
            }
        }
        // Filter by product subCategory
        iter = products.listIterator();
        if (subCategoryFilter) {
            while (iter.hasNext()) {
                Product product = iter.next();
                Category category = product.getProductCategory();
                if (!category.getSubCategoryName().equalsIgnoreCase(subCategoryName)) {
                    iter.remove();
                }
            }
        }
        // Filter by product tags
        iter = products.iterator();
        if (tagFilter) {
            while (iter.hasNext()) {
                Product product = iter.next();
                List<ProductTag> productTags = product.getProductTags();
                boolean notFound = true;
                for (ProductTag productTag : productTags) {
                    if (productTag.getTagContents().equalsIgnoreCase(tag)) {
                        notFound = false;
                        break;
                    }
                }
                if (notFound) {
                    iter.remove();
                }
            }
        }
        // Filter by product price
        iter = products.listIterator();
        if (priceFilter) {
            while (iter.hasNext()) {
                Product product = iter.next();
                if (product.getProductPrice() <= lowerPrice_i || product.getProductPrice() >= higherPrice_i) {
                    iter.remove();
                }
            }
        }
        // create tag List, subCategory List
        Set<String> tagList = new HashSet<>();
        Set<String> subCategoryList = new HashSet<>();
        for (Product product : products) {
            subCategoryList.add(product.getProductCategory().getSubCategoryName());
            List<ProductTag> productTags = product.getProductTags();
            if (productTags != null && !productTags.isEmpty()) {
                for (ProductTag productTag : productTags) {
                    tagList.add(productTag.getTagContents());
                }
            }
        }
        // sort product
        if (needSort) {
            products = productService.sort(products, sortType);
        }

        model.addAttribute("products", products);
        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("tagList", tagList);

        return "products";
    }

    /*
     * viewProduct.jsp modelAttribute: product(Product),
     * productCommentList(List<ProductComment>, productCategory(Category))
     * productRelatedList
     */
    @GetMapping("/p")
    public String viewProduct(@RequestParam(value = "id", required = true) Long productId, Model model, HttpServletRequest request)
            throws IOException {
        Product product = productService.getProductById(productId);
        productService.getVisited(productId);
        model.addAttribute("product", product);
        model.addAttribute("customer", product);
        model.addAttribute("productCommentList", productCommentRepository.findByProduct(product));
        model.addAttribute("productCategory", product.getProductCategory());

        // related product according to the same category
        model.addAttribute("productRelated", productService.getProductsByCategory(product.getProductCategory()));

        // provide new comment model
        if (request.getSession().getAttribute("customerId_") != null) {
            ProductComment productComment = new ProductComment();
            productComment.setCustomer(customerService.findById(Long.parseLong(request.getSession().getAttribute("customerId_").toString(), 10)));
            model.addAttribute("productComment", productComment);
        }
        return "product-detail";
    }

    @PostMapping(value = "/p")
    public String addProductComment(@RequestParam(value = "id", required = true) Long productId,
            @Valid @ModelAttribute("productComment") ProductComment productComment) {
        productComment.setCommentDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        Product product = productService.getProductById(productId);

        productComment.setProduct(product);
        productCommentRepository.save(productComment);
        return "redirect:/pd/p?id=" + productId;
    }
}
