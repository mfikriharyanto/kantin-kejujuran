package com.project.kantinkejujuran.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.kantinkejujuran.dto.UserDto;
import com.project.kantinkejujuran.model.Role;
import com.project.kantinkejujuran.model.User;
import com.project.kantinkejujuran.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        userDto = new UserDto("12306", "1234567890");
    }

    @Test
    public void testSaveUserSuccess() throws Exception {
        userDto.setId("12306");
        userService.save(userDto);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSaveUserIdNotValid() {
        userDto.setId("12345");
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals(thrown.getMessage(),"ID is not valid");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testSaveUserIdIsNotNumber() {
        userDto.setId("aaaaa");
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals(thrown.getMessage(),"ID may only contain numbers");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testSaveUserIdLessThanFiveDigits() {
        userDto.setId("123");
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals(thrown.getMessage(),"ID should consist of a 5 digits number");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testSaveUserIdMoreThanFiveDigits() {
        userDto.setId("123456");
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals(thrown.getMessage(),"ID should consist of a 5 digits number");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testSaveUserIdIsRegistered() {
        userDto = new UserDto();
        userDto.setId("12306");
        Optional<User> user = Optional.of(new User("12306", "1234567890", Role.USER));
        when(userRepository.findById("12306")).thenReturn(user);
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals(thrown.getMessage(),"ID is already registered");
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void testValidateValidUserId() {
        Boolean result = userService.validateUserId("42107");
        assertTrue(result);
    }

    @Test
    public void testValidateNotValidUserId() {
        Boolean result = userService.validateUserId("45111");
        assertFalse(result);
    }

    @Test
    public void testValidateIdLessThanFiveDigits() {
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.validateUserId("321");
        });
        assertEquals(thrown.getMessage(),"ID should consist of a 5 digits number");
    }

    @Test
    public void testValidateIdMoreThanFiveDigits() {
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.validateUserId("654321");
        });
        assertEquals(thrown.getMessage(),"ID should consist of a 5 digits number");
    }

    @Test
    public void testValidateIsNotNumber() {
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.validateUserId("abcde");
        });
        assertEquals(thrown.getMessage(),"ID may only contain numbers");
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("54321");
        });
        assertEquals(thrown.getMessage(),"Invalid ID or password");
    }

    @Test
    public void testLoadUserByUsernameFound() {
        User user = new User("12306", "1234567890", Role.USER);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById("12306")).thenReturn(userOptional);
        User userDetails = (User) userService.loadUserByUsername("12306");
        assertEquals("12306", userDetails.getId());
        assertEquals("1234567890", userDetails.getPassword());
        assertEquals(Role.USER, userDetails.getRole());
    }

    @Test
    public void testGetUserByIdNotFound() {
        User user = userService.getUserById("54321");
        assertNull(user);
    }

    @Test
    public void testGetUserByIdFound() {
        User user = new User("12306", "1234567890", Role.USER);
        Optional<User> userOptional = Optional.of(user);
        when(userRepository.findById("12306")).thenReturn(userOptional);
        
        User userTest = userService.getUserById("12306");

        assertEquals(user.getId(), userTest.getId());
        assertEquals(user.getPassword(), userTest.getPassword());
        assertEquals(user.getRole(), userTest.getRole());

        verify(userRepository, times(1)).findById(any(String.class));
    }
}