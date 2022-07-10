package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void save(ProductDto productDto) throws IOException, IllegalArgumentException;

    void delete(String productId) throws IllegalArgumentException;

    List<Product> getAllProduct();
    
    Product getProductById(String productId);
}
