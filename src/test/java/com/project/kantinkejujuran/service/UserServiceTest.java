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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto("12306", "1234567890");
    }

    @Test
    void testSaveUserSuccess() throws Exception {
        userDto.setId("12306");
        userService.save(userDto);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @ParameterizedTest
    @CsvSource({
        "12345, ID is not valid",
        "aaaaa, ID may only contain numbers",
        "123, ID should consist of a 5 digits number",
        "123456, ID should consist of a 5 digits number",
    })
    void testSaveUserIdNotValid(String id, String expectation) {
        userDto.setId(id);
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals(expectation, thrown.getMessage());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testSaveUserIdIsRegistered() {
        userDto = new UserDto();
        userDto.setId("12306");
        Optional<User> user = Optional.of(new User("12306", "1234567890", Role.USER));
        when(userRepository.findById("12306")).thenReturn(user);
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.save(userDto);
        });
        assertEquals("ID is already registered", thrown.getMessage());
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void testValidateValidUserId() {
        Boolean result = userService.validateUserId("42107");
        assertTrue(result);
    }

    @Test
    void testValidateNotValidUserId() {
        Boolean result = userService.validateUserId("45111");
        assertFalse(result);
    }

    @Test
    void testValidateIdLessThanFiveDigits() {
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.validateUserId("321");
        });
        assertEquals("ID should consist of a 5 digits number", thrown.getMessage());
    }

    @Test
    void testValidateIdMoreThanFiveDigits() {
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.validateUserId("654321");
        });
        assertEquals("ID should consist of a 5 digits number", thrown.getMessage());
    }

    @Test
    void testValidateIsNotNumber() {
        Exception thrown = assertThrows(Exception.class, () -> {
            userService.validateUserId("abcde");
        });
        assertEquals("ID may only contain numbers", thrown.getMessage());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("54321");
        });
        assertEquals("Invalid ID or password", thrown.getMessage());
    }

    @Test
    void testLoadUserByUsernameFound() {
        User user = new User("12306", "1234567890", Role.USER);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById("12306")).thenReturn(userOptional);
        User userDetails = (User) userService.loadUserByUsername("12306");
        assertEquals("12306", userDetails.getId());
        assertEquals("1234567890", userDetails.getPassword());
        assertEquals(Role.USER, userDetails.getRole());
    }

    @Test
    void testGetUserByIdNotFound() {
        User user = userService.getUserById("54321");
        assertNull(user);
    }

    @Test
    void testGetUserByIdFound() {
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