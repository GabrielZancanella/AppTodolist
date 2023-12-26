package com.AppTodolist.AppTodolist.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.TaskRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;

@Controller
@RequestMapping("/admin/") // Adicionando "admin/" ao mapeamento
public class AdminHome {
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;
    
    // CREATE
    @GetMapping("/home")
    public String showLoginForm(Model model) {
    	long totalUsuarios 	= userRepository.count();
    	long totalListas 	= taskListRepository.count();
    	long totalTarefas 	= taskRepository.count();
    	
    	model.addAttribute("totalUsuarios"	, totalUsuarios);
    	model.addAttribute("totalListas"	, totalListas  );
    	model.addAttribute("totalTarefas"	, totalTarefas );
        return "/admin/index";
    }

}
