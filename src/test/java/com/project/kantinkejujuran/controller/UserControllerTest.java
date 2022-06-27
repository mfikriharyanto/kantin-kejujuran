package com.project.kantinkejujuran.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.project.kantinkejujuran.dto.UserDto;
import com.project.kantinkejujuran.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class UserControllerTest {

    @SpyBean
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

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
    public void testGetRegisterShouldReturnRegisterPage() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("registerUser")))
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("register"));
    }

    @Test
    @WithAnonymousUser
    public void testPostRegisterItShouldAddNewUser() throws Exception {
        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .param("id", "12306")
                        .param("password", "1234567890"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("registerUser")))
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("success"));
    }

    @Test
    @WithAnonymousUser
    public void testPostRegisterItShouldFailed() throws Exception {
        assertThrows(Exception.class, () -> userService.save(any(UserDto.class)));
        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .param("id", "aaaaa")
                        .param("password", "1234567890"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("registerUser")))
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetRegisterWithLoggedUser() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isForbidden());
    }
}