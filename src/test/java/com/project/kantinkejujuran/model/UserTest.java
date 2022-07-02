package com.project.kantinkejujuran.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

class UserTest {
    private Class<?> userClass;
    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        userClass = Class.forName("com.project.kantinkejujuran.model.User");
        user = new User("12345", "12345", Role.USER);
    }

    @Test
    public void testUserIsConcreteClass() {
        assertFalse(Modifier.isAbstract(userClass.getModifiers()));
    }

    @Test
    public void testUserItemIsAUserDetails() {
        Collection<Type> interfaces = Arrays.asList(userClass.getInterfaces());

        assertTrue(interfaces.stream().anyMatch(type -> type.getTypeName()
                .equals("org.springframework.security.core.userdetails.UserDetails")));
    }

    @Test
    void testGetAuthoritiesShouldReturnCorrectly() {
        Collection<? extends GrantedAuthority> authentication = user.getAuthorities();
        assertEquals(Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())), authentication);
    }

    @Test
    public void testGetUsernameShouldReturnCorrectly() {
        String username = user.getUsername();
        assertEquals("12345", username);
    }

    @Test
    public void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    public void testGetIdShouldReturnCorrectly() {
        String id = user.getId();
        assertEquals("12345", id);
    }

    @Test
    public void testGetPasswordShouldReturnCorrectly() {
        String password = user.getPassword();
        assertEquals("12345", password);
    }

    @Test
    public void testGetRoleShouldReturnCorrectly() {
        Role role = user.getRole();
        assertEquals(Role.USER, role);
    }

    @Test
    public void testSetIdShouldChangeId() {
        user = new User();
        user.setId("54321");
        String id = user.getId();
        assertEquals("54321", id);
    }

    @Test
    void testSetPasswordShouldChangePassword() {
        user = new User();
        user.setPassword("54321");
        String password = user.getPassword();
        assertEquals("54321", password);
    }

    @Test
    void testSetRoleShouldChangeRole() {
        user = new User();
        user.setRole(Role.ADMIN);
        Role role = user.getRole();
        assertEquals(Role.ADMIN, role);
    }
}