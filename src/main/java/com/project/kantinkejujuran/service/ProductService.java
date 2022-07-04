package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;

import java.util.List;

public interface ProductService {
    void save(ProductDto productDto) throws Exception;

    void delete(String productId) throws Exception;

    List<Product> getAllProduct();
    
    Product getProductById(String productId);
}
