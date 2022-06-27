package com.project.kantinkejujuran.controller;

import com.project.kantinkejujuran.dto.UserDto;
import com.project.kantinkejujuran.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String registerUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping
    public String registerUser(Model model, @ModelAttribute("user")UserDto userDto) {
        try {
            userService.save(userDto);
            model.addAttribute("success", true);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("user", new UserDto());
        return "register";
    }
}
