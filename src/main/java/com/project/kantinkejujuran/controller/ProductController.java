package com.project.kantinkejujuran.controller;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;
import com.project.kantinkejujuran.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/store")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String store(Model model) {
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("productList", productList);
        return "store";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new ProductDto());
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("user") ProductDto productDto) {
        productService.save(productDto);
        return "redirect:/store";
    }

    @PostMapping("/buy")
    public String buyProduct(@ModelAttribute("productId") String productId) {
        productService.delete(productId);
        return "redirect:/store";
    }

}
