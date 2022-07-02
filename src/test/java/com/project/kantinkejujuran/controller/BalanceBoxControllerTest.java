package com.project.kantinkejujuran.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.project.kantinkejujuran.service.BalanceBoxServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class BalanceBoxControllerTest {

    @SpyBean
    private BalanceBoxServiceImpl balanceBoxService;

    @InjectMocks
    private BalanceBoxController balanceBoxController;

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
    public void testGetBalanceBoxPageShouldRedirect() throws Exception {
        mockMvc.perform(get("/balance-box"))
                .andExpect(status().is(302));
    }

    @Test
    @WithAnonymousUser
    public void testGetAddBalanceBoxPageShouldRedirect() throws Exception {
        mockMvc.perform(post("/balance-box/add")
                        .with(csrf())
                        .param("change", "10000"))
                .andExpect(status().is(302));
    }

    @Test
    @WithAnonymousUser
    public void testGetWithdrawBalanceBoxPageShouldRedirect() throws Exception {
        mockMvc.perform(post("/balance-box/withdraw")
                        .with(csrf())
                        .param("change", "10000"))
                .andExpect(status().is(302));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetBalanceBoxPageSuccess() throws Exception {
        mockMvc.perform(get("/balance-box"))
                .andExpect(status().isOk())
                .andExpect((handler().methodName("balanceBox")))
                .andExpect(model().attributeExists("total"))
                .andExpect(view().name("balance_box"));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostAddBalanceBoxSuccess() throws Exception {
        mockMvc.perform(post("/balance-box/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("10000"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostAddBalanceBoxWithNegativeNumber() throws Exception {
        mockMvc.perform(post("/balance-box/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("-10000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostWithdrawBalanceBoxSuccess() throws Exception {
        when(balanceBoxService.getLastTotal()).thenReturn(20000L);
        mockMvc.perform(post("/balance-box/withdraw")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("10000"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostWithdrawBalanceBoxWithNegativeNumber() throws Exception {
        mockMvc.perform(post("/balance-box/withdraw")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("-10000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testPostWithdrawBalanceBoxLessTotal() throws Exception {
        when(balanceBoxService.getLastTotal()).thenReturn(5000L);
        mockMvc.perform(post("/balance-box/withdraw")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("-10000"))
                .andExpect(status().isBadRequest());
    }
}