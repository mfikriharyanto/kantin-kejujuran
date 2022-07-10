package com.project.kantinkejujuran.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.model.Product;
import com.project.kantinkejujuran.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Sate", "Terbuat dari daging pilihan", 10000, "");
    }

    @Test
    void testSaveProductSuccess() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "test.png",
                String.valueOf(MediaType.IMAGE_PNG), "image".getBytes());

        ProductDto productDto = new ProductDto();
        productDto.setName("Bakso");
        productDto.setDescription("Terbuat dari daging berkualitas");
        productDto.setPrice(15000);
        productDto.setImage(image);
        productService.save(productDto);

        productDto = new ProductDto("Mie ayam", "Terbuat dari bahan berkualitas", 10000, image);
        productService.save(productDto);
        verify(productRepository, times(2)).save(any(Product.class));
    }

    @Test
    void testSaveProductInvalidPrice() {
        MockMultipartFile image = new MockMultipartFile("image", "test.png",
                String.valueOf(MediaType.IMAGE_PNG), "image".getBytes());

        ProductDto productDto = new ProductDto("Mie ayam",
                "Terbuat dari bahan berkualitas", -10000, image);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            productService.save(productDto);
        });
        assertEquals("Invalid price", thrown.getMessage());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testSaveProductInvalidImage() {
        MockMultipartFile text = new MockMultipartFile("text", "test.txt",
                String.valueOf(MediaType.TEXT_PLAIN), "text".getBytes());

        ProductDto productDto = new ProductDto("Mie ayam",
                "Terbuat dari bahan berkualitas", 10000, text);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            productService.save(productDto);
        });
        assertEquals("Invalid image", thrown.getMessage());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testDeleteProductById() throws Exception {
        Optional<Product> productOptional = Optional.of(product);
        when(productRepository.findById(anyString())).thenReturn(productOptional);
        productService.delete("1234567890");
        verify(productRepository, times(1)).delete(any(Product.class));
    }

    @Test
    void testDeleteProductByIdFailed() {
        Exception thrown = assertThrows(Exception.class, () -> {
            productService.delete("1234567890");
        });
        assertEquals("Invalid id", thrown.getMessage());
        verify(productRepository, times(0)).delete(any(Product.class));
    }

    @Test
    void testGetAllProduct() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> productList = productService.getAllProduct();
        assertEquals(1, productList.size());
        assertEquals(List.of(product), productList);

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductByIdNotFound() {
        Product product = productService.getProductById("1234567890");
        assertNull(product);
    }

    @Test
    void testGetProductByIdFound() {
        Optional<Product> productOptional = Optional.of(product);
        when(productRepository.findById(anyString())).thenReturn(productOptional);
        Product productTest = productService.getProductById("1234567890");

        assertEquals(product.getName(), productTest.getName());
        assertEquals(product.getDescription(), productTest.getDescription());
        assertEquals(product.getPrice(), productTest.getPrice());

        verify(productRepository, times(1)).findById(any(String.class));
    }
}