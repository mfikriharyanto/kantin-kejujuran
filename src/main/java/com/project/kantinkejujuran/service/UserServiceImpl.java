package com.project.kantinkejujuran.service;

import com.project.kantinkejujuran.dto.UserDto;
import com.project.kantinkejujuran.model.Role;
import com.project.kantinkejujuran.model.User;
import com.project.kantinkejujuran.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public void save(UserDto userDto) throws Exception {
        Boolean correctUserId = validateUserId(userDto.getId());
        if (!correctUserId) {
            throw new Exception("ID is not valid");
        }

        User user = getUserById(userDto.getId());
        if (user != null) {
            throw new Exception("ID is already registered");
        }

        user = new User(userDto.getId(),
                bcryptPasswordEncoder.encode(userDto.getPassword()),
                Role.USER);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = getUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid ID or password");
        }

        return new User(user.getId(), user.getPassword(), user.getRole());
    }

    @Override
    public Boolean validateUserId(String userId) throws IllegalArgumentException {
        try {
            int userIdInteger = Integer.parseInt(userId);

            if (userId.length() != 5) {
                throw new IllegalArgumentException("ID should consist of a 5 digits number");
            }

            int sumOfFirstThreeDigits = 0;
            int twoLastDigits = userIdInteger % 100;

            userIdInteger /= 100;
            while (userIdInteger > 0) {
                sumOfFirstThreeDigits += userIdInteger % 10;
                userIdInteger /= 10;
            }

            return sumOfFirstThreeDigits == twoLastDigits;

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID may only contain numbers");
        }
    }

    @Override
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
