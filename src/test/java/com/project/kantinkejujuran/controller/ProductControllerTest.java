package com.project.kantinkejujuran.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.project.kantinkejujuran.dto.ProductDto;
import com.project.kantinkejujuran.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class ProductControllerTest {

    @MockBean
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithAnonymousUser
    public void testGetStorePageShouldReturnStorePage() throws Exception {
        mockMvc.perform(get("/store"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("store")))
                .andExpect(model().attributeExists("productList"))
                .andExpect(view().name("store"));
    }

    @Test
    @WithAnonymousUser
    public void testGetAddProductPageShouldRedirect() throws Exception {
        mockMvc.perform(get("/store/add"))
                .andExpect(status().is(302));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetAddProductPageShouldReturnAddProductPage() throws Exception {
        mockMvc.perform(get("/store/add"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("addProduct")))
                .andExpect(model().attributeExists("product"))
                .andExpect(view().name("add_product"));
    }

    @Test
    @WithAnonymousUser
    public void testPostAddProductPageShouldRedirect() throws Exception {
        mockMvc.perform(post("/store/add")
                        .with(csrf())
                        .param("name", "Bakso")
                        .param("description", "Terbuat dari daging berkualitas")
                        .param("price", "100000"))
                .andExpect(status().is(302));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostAddProductPageWithLoggedUser() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "test.png",
                String.valueOf(MediaType.IMAGE_PNG), "image".getBytes());
        mockMvc.perform(multipart("/store/add")
                        .file(image)
                        .with(csrf())
                        .param("name", "Bakso")
                        .param("description", "Terbuat dari daging berkualitas")
                        .param("price", "100000"))
                .andExpect(status().is(302))
                .andExpect((handler().methodName("addProduct")))
                .andExpect(view().name("redirect:/store"))
                .andExpect(redirectedUrl("/store"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostAddProductPageWithLoggedUserFailed() throws Exception {
        doThrow(IllegalArgumentException.class).when(productService).save(any(ProductDto.class));
        MockMultipartFile text = new MockMultipartFile("text", "test.txt",
                String.valueOf(MediaType.TEXT_PLAIN), "text".getBytes());
        mockMvc.perform(multipart("/store/add")
                        .file(text)
                        .with(csrf())
                        .param("name", "Bakso")
                        .param("description", "Terbuat dari daging berkualitas")
                        .param("price", "100000"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("addProduct")))
                .andExpect(view().name("add_product"));
    }

    @Test
    @WithAnonymousUser
    public void testGetBuyProductWithAnonymousUser() throws Exception {
        mockMvc.perform(post("/store/buy")
                        .with(csrf())
                        .param("productId", "1234567890"))
                .andExpect(status().is(302));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetBuyProductWithLoggedUser() throws Exception {
        mockMvc.perform(post("/store/buy")
                        .with(csrf())
                        .param("productId", "1234567890"))
                .andExpect(status().is(302))
                .andExpect((handler().methodName("buyProduct")))
                .andExpect(view().name("redirect:/store"))
                .andExpect(redirectedUrl("/store"));
    }

}