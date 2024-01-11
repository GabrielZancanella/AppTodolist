package com.AppTodolist.AppTodolist.model.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Tasks;
import com.AppTodolist.AppTodolist.model.UserRole;
import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.model.userContext;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.TaskRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/")
    public String showLoginForm(Model model) {
        return "login";
    }

    @PostMapping("/")
    public String login(String username, String password, Model model) {
        userContext.clearCurrentUser();

        if (username == null || password == null) {
            return "error";
        }

        Users user = userRepository.findByUsername(username);

        if (user == null || !user.getSenha().equals(password)) {
            return "error";
        }

        userContext.setCurrentUser(user);

        if (user.getRole() == UserRole.ADMIN) {
        	return "redirect:/admin/home";
        } else if (user.getRole() == UserRole.USER) {
        	
            List<TaskLists> taskLists = taskListRepository.findByUser_id(user.getId());

            List<Tasks> tasks = new ArrayList<>();

            for (TaskLists taskList : taskLists) {
                List<Tasks> tasksForList = taskRepository.findByTaskList(taskList);
                tasks.addAll(tasksForList);
            }
            
            model.addAttribute("user", user);
            model.addAttribute("userId", user.getId());
            model.addAttribute("taskLists", taskLists);
            model.addAttribute("tasks", tasks);
            return "redirect:/user/home";
        } else {
            return "error";
        }
    }
}