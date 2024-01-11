package com.AppTodolist.AppTodolist.model.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.UserRole;
import com.AppTodolist.AppTodolist.model.UserTaskDto;
import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;

@Controller
@RequestMapping("/admin/user") // Adicionando "admin/" ao mapeamento
public class AdminUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListsRepository;

    // CREATE
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
    	model.addAttribute("role", UserRole.values());
        model.addAttribute("mensagemErro", "");
        model.addAttribute("user", new Users());
        return "admin/user/add-user";
    }

    // Método POST para processar a adição de usuários
    @PostMapping("/add")
    public String addUser(@ModelAttribute Users user, Model model) {
    	
        if (user.getRole() == null) {
            user.setRole(UserRole.USER); // Padrão para USER se não for selecionado
        }
        
        if (!(user.getEmail().contains("@"))) {
        	user.setEmail("");
        	model.addAttribute("user", user);
            model.addAttribute("mensagemErro", "Não possui @!");
            return "admin/user/add-user";
        }
        
        if (!(user.getEmail().contains("outlook")) && !(user.getEmail().contains("gmail"))) {
        	user.setEmail("");
        	model.addAttribute("user", user);
            model.addAttribute("mensagemErro", "Não possui provedor!");
            return "admin/user/add-user";
        }
    	
        userRepository.save(user);
        return "redirect:/admin/user/list"; // Adicionando "admin/" ao redirecionamento
    }

    // READ
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<Users> users = userRepository.findAll();
        List<TaskLists> usersTaskList = new ArrayList<>();


        for (Users user : users) {
            List<TaskLists> userTaskLists = taskListsRepository.findByUser_id(user.getId());

            // Adiciona à lista de DTOs
            for (TaskLists taskList : userTaskLists) {
            //    UserTaskDto userTaskDto = new UserTaskDto(user, taskList.getName());
            //    userTaskDtoList.add(userTaskDto);
            	usersTaskList.add(taskList);
            }
        }

        //model.addAttribute("userTaskDtoList", userTaskDtoList);
        model.addAttribute("usersTaskList", usersTaskList);
        model.addAttribute("users", users);
        

        return "admin/user/userList"; // Adicionando "admin/" ao redirecionamento
    }

    // READ
    @GetMapping("/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        Optional<Users> currentUser = userRepository.findById(id);
        Users user = currentUser.orElse(null);

        if (user == null) {
            return "redirect:/admin/user/list"; // Adicionando "admin/" ao redirecionamento
        }

        List<TaskLists> taskLists = taskListsRepository.findByUser_id(user.getId());
        String taskNames = taskLists.stream().map(TaskLists::getName).collect(Collectors.joining("\n "));

        UserTaskDto userTaskDto = new UserTaskDto(user, taskNames);

        model.addAttribute("userTaskDto", userTaskDto);
        return "admin/user/userDetails"; // Adicionando "admin/" ao redirecionamento
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
            return "redirect:/admin/user/list"; // Adicionando "admin/" ao redirecionamento
        }

        model.addAttribute("updatedUser", user);
        return "admin/user/update-user"; // Adicionando "admin/" ao redirecionamento
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

        return "redirect:/admin/user/list"; // Adicionando "admin/" ao redirecionamento
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/user/list"; // Adicionando "admin/" ao redirecionamento
    }
}
