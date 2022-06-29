package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;
import com.project.kantinkejujuran.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(ProductDto productDto) {
        Product product = new Product(productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice());
        productRepository.save(product);
    }

    @Override
    public void delete(String productId) {
        Product product = this.getProductById(productId);
        if (product != null) {
            productRepository.delete(product);
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
