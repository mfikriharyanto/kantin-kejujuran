package com.project.kantinkejujuran.controller;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;
import com.project.kantinkejujuran.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String addProduct(Model model, @ModelAttribute("user") ProductDto productDto) {
        try {
            productService.save(productDto);
            return "redirect:/store";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "add_product";
        }
    }

    @PostMapping(path = "/buy", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity buyProduct(@RequestBody String productId) {
        try {
            productService.delete(productId);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
