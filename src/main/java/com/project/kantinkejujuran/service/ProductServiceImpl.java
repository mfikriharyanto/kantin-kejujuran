package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;
import com.project.kantinkejujuran.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(ProductDto productDto) throws Exception {
        if (!productDto.getImage().getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Invalid image");
        } else if (productDto.getPrice() < 0) {
            throw new IllegalArgumentException("Invalid price");
        }
        String image = Base64.getEncoder()
                .encodeToString(productDto.getImage().getBytes());
        Product product = new Product(productDto.getName(),
                productDto.getDescription(), productDto.getPrice(), image);
        productRepository.save(product);
    }

    @Override
    public void delete(String productId) throws Exception {
        Product product = this.getProductById(productId);
        if (product == null) {
            throw new Exception("Invalid id");
        }
        productRepository.delete(product);
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
