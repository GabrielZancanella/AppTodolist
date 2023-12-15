package com.AppTodolist.AppTodolist.model.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private TaskListRepository taskListsRepository;
    
    // CREATE
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new Users());
        return "user/add-user"; 
    }

    // Método POST para processar a adição de usuários
    @PostMapping("/add")
    public String addUser(@ModelAttribute Users user) {
        userRepository.save(user);
        return "redirect:/user/list";
    }
    
    // READ
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<Users>users = userRepository.findAll();
        model.addAttribute("users", users);
        

        List<TaskLists> tasklist = taskListsRepository.findAll();
        model.addAttribute("tasklist", tasklist);
        
        return "user/userList"; 
    }

    // READ
    @GetMapping("/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
    	Optional<Users> currentUser = userRepository.findById(id); 
    	Users user = null;
    	
    	if (currentUser.isPresent()) {
    	    user = currentUser.get();
    	}
    	
        if (user == null) {
            return "redirect:/user/list";
        }

        model.addAttribute("user", user);
        return "user/userDetails"; 
    }

    // UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
    	Optional<Users> currentUser = userRepository.findById(id); 
    	Users user = null;
    	
    	if (currentUser.isPresent()) {
    	    user = currentUser.get();
    	}
    	
        if (user == null) {
            return "redirect:/user/list";
        }

        model.addAttribute("updatedUser", user);
        return "user/update-user"; // Nome da página de atualização de usuário (crie conforme necessário)
    }

    // POST para processar a atualização do usuário
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute Users updatedUser) {
    	Optional<Users> currentUser = userRepository.findById(id); 
    	
    	if (currentUser.isPresent()) {
    	    Users user = currentUser.get();
            
    	    if (user != null) {
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                user.setSenha(updatedUser.getSenha());
                user.setTaskLists(updatedUser.getTaskLists());

                userRepository.save(user);
            }
    	}

        return "redirect:/user/list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
    	userRepository.deleteById(id);
        return "redirect:/user/list";
    }
}
