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
@RequestMapping("/user/user") // Adicionando "user/" ao mapeamento
public class UserUserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListsRepository;

    // CREATE
    @GetMapping("/add")
    public String showAddUserForm(Model model) {
    	model.addAttribute("role", UserRole.values());
        model.addAttribute("user", new Users());
        return "user/user/add-user";
    }

    // Método POST para processar a adição de usuários
    @PostMapping("/add")
    public String addUser(@ModelAttribute Users user) {
    	
        if (user.getRole() == null) {
            user.setRole(UserRole.USER); // Padrão para USER se não for selecionado
        }
    	
        userRepository.save(user);
        return "redirect:/user/user/list"; // Adicionando "user/" ao redirecionamento
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
        

        return "user/user/userList"; // Adicionando "user/" ao redirecionamento
    }

    // READ
    @GetMapping("/{id}")
    public String userDetails(@PathVariable Long id, Model model) {
        Optional<Users> currentUser = userRepository.findById(id);
        Users user = currentUser.orElse(null);

        if (user == null) {
            return "redirect:/user/user/list"; // Adicionando "user/" ao redirecionamento
        }

        List<TaskLists> taskLists = taskListsRepository.findByUser_id(user.getId());
        String taskNames = taskLists.stream().map(TaskLists::getName).collect(Collectors.joining("\n "));

        UserTaskDto userTaskDto = new UserTaskDto(user, taskNames);

        model.addAttribute("userTaskDto", userTaskDto);
        return "user/user/userDetails"; // Adicionando "user/" ao redirecionamento
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
            return "redirect:/user/user/list"; // Adicionando "user/" ao redirecionamento
        }

        model.addAttribute("updatedUser", user);
        return "user/user/update-user"; // Adicionando "user/" ao redirecionamento
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

        return "redirect:/user/user/list"; // Adicionando "user/" ao redirecionamento
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/user/user/list"; // Adicionando "user/" ao redirecionamento
    }
}
