package com.project.kantinkejujuran.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String homepage() {
        return "homepage";
    }

    @GetMapping("/login")
    public String login() {
        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
 
        return "redirect:/";
    }

}
