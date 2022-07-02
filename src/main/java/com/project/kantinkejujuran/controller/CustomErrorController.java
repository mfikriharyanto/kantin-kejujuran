package com.project.kantinkejujuran.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {
 
    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        status = status != null ? status : 200;
        String message = HttpStatus.valueOf(status).getReasonPhrase();
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return "error";
    }

}