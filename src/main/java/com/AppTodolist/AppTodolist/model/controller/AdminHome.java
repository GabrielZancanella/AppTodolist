package com.AppTodolist.AppTodolist.model.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/") // Adicionando "admin/" ao mapeamento
public class AdminHome {
	
    // CREATE
    @GetMapping("/home")
    public String showLoginForm(Model model) {
        return "/admin/index";
    }

}
