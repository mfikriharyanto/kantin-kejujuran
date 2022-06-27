package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.dto.UserDto;
import com.project.kantinkejujuran.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(UserDto userDto) throws Exception;

    Boolean validateUserId(String userId) throws IllegalArgumentException;

    User getUserById(String userId);
}
