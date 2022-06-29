package com.project.kantinkejujuran.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Date;

public class ProductTest {
    private Class<?> productClass;
    private Product product;

    @BeforeEach
    public void setUp() throws Exception {
        productClass = Class.forName("com.project.kantinkejujuran.model.Product");
        product = new Product("Bakso", "Terbuat dari daging berkualitas", 10000);
    }

    @Test
    public void testUserIsConcreteClass() {
        assertFalse(Modifier.isAbstract(productClass.getModifiers()));
    }

    @Test
    public void testGetIdShouldReturnCorrectly() {
        String id = product.getId();
        assertNull(id);
    }

    @Test
    public void testGetNameShouldReturnCorrectly() {
        String name = product.getName();
        assertEquals("Bakso", name);
    }

    @Test
    public void testGetDescriptionShouldReturnCorrectly() {
        String description = product.getDescription();
        assertEquals("Terbuat dari daging berkualitas", description);
    }

    @Test
    public void testGetPriceShouldReturnCorrectly() {
        Integer price = product.getPrice();
        assertEquals(10000, price);
    }

    @Test
    public void testGetCreatedDateShouldReturnCorrectly() {
        Date date = product.getDateCreated();
        assertNull(date);
    }

    @Test
    void testSetIdShouldChangePassword() {
        product = new Product();
        product.setId("1234567890");
        String id = product.getId();
        assertEquals("1234567890", id);
    }

    @Test
    void testSetNameShouldChangePassword() {
        product = new Product();
        product.setName("Sate");
        String name = product.getName();
        assertEquals("Sate", name);
    }

    @Test
    void testSetDescriptionShouldChangePassword() {
        product = new Product();
        product.setDescription("Terbuat dari daging pilihan");
        String description = product.getDescription();
        assertEquals("Terbuat dari daging pilihan", description);
    }

    @Test
    void testSetPriceShouldChangePassword() {
        product = new Product();
        product.setPrice(15000);
        Integer price = product.getPrice();
        assertEquals(15000, price);
    }

    @Test
    void testSetDateShouldChangePassword() {
        Date currentDate = new Date();
        product = new Product();
        product.setDateCreated(currentDate);
        assertEquals(currentDate, product.getDateCreated());
    }
}