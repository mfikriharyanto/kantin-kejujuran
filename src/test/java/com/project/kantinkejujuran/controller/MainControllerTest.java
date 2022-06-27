package com.project.kantinkejujuran.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class MainControllerTest {

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
    public void testGetHomePageShouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("homepage")))
                .andExpect(view().name("homepage"));
    }

    @Test
    @WithAnonymousUser
    public void testGetLoginShouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("login")))
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetLoginShouldRedirectToHomePage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is(302))
                .andExpect((handler().methodName("login")))
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));
    }
}