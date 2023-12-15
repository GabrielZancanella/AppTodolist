package com.AppTodolist.AppTodolist.model.controller;

import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.model.userContext;
import com.AppTodolist.AppTodolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    // CREATE
    @GetMapping("/")
    public String showLoginForm(Model model) {
        return "login";
    }

    // Método POST para processar login
    @PostMapping("/")
    public String login(String username, String password, Model model) {
        userContext.clearCurrentUser();

        if (username == null || password == null) {
            return "error";
        }

        Users user = userRepository.findByUsername(username);

        if (user == null) {
            return "error";
        }

        return "index";
    }
}
