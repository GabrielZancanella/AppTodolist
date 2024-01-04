package com.AppTodolist.AppTodolist.model.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Tasks;
//import com.AppTodolist.AppTodolist.model.UserRole;
import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.TaskRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;
//import com.AppTodolist.AppTodolist.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserHome {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/home")
    public String showLoginForm(Model model) {
        Users user = userRepository.findByUsername("a");//(Users) model.getAttribute("user");
        List<TaskLists> taskLists = taskListRepository.findByUser_id(user.getId());
        
        List<Tasks> tasks = new ArrayList<>();

        for (TaskLists taskList : taskLists) {
            List<Tasks> tasksForList = taskRepository.findByTaskList(taskList);
            tasks.addAll(tasksForList);
        }

        model.addAttribute("taskLists", taskLists);
        model.addAttribute("user", user);
        model.addAttribute("tasks", tasks);
        return "user/index";
    }

}
