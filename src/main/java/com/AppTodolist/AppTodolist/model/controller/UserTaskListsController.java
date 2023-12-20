package com.AppTodolist.AppTodolist.model.controller;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user/tasklist") 
public class UserTaskListsController {

    @Autowired
    private TaskListRepository taskListsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String showAddTaskListForm(Model model) {
    	List<Users> userList = userRepository.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("taskList", new TaskLists());
        return "user/tasklist/add-tasklist";
    }

    // Método POST para processar a adição de lista de tarefas
    @PostMapping("/add")
    public String addTaskList(@ModelAttribute TaskLists taskList) {
        taskListsRepository.save(taskList);
        return "redirect:/user/tasklist/list"; 
    }

    // READ
    @GetMapping("/list")
    public String listTaskLists(Model model) {
        List<TaskLists> taskLists = null;
        taskLists = taskListsRepository.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (taskLists != null) {
            taskLists.forEach(taskList -> {
                if (taskList.getCreateDate() != null) {
                    taskList.setFormattedCreateDate(taskList.getCreateDate().format(formatter));
                }
            });
        }
        	
        model.addAttribute("taskLists", taskLists);
        return "user/tasklist/tasklist_list"; // Adicionando "user/" ao redirecionamento
    }

    // READ
    @GetMapping("/{id}")
    public String taskListDetails(@PathVariable Long id, Model model) {
        TaskLists taskList = taskListsRepository.findById(id).orElse(null);

        if (taskList == null) {
            return "redirect:/user/error"; // Adicionando "user/" ao redirecionamento
        }

        model.addAttribute("taskList", taskList);
        return "user/tasklist/taskListDetails"; // Adicionando "user/" ao redirecionamento
    }

    // UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateTaskListForm(@PathVariable Long id, Model model) {
        TaskLists taskList = taskListsRepository.findById(id).orElse(null);
        List<Users> userList = userRepository.findAll();
        if (taskList == null) {
            return "redirect:/user/error"; // Adicionando "user/" ao redirecionamento
        }

        model.addAttribute("userList", userList);
        model.addAttribute("taskList", taskList);
        return "user/tasklist/update-tasklist"; // Adicionando "user/" ao redirecionamento
    }

    // POST para processar a atualização da lista de tarefas
    @PostMapping("/update/{id}")
    public String updateTaskList(@PathVariable Long id, @ModelAttribute TaskLists updatedTaskList) {
        Optional<TaskLists> currentTaskListOptional = taskListsRepository.findById(id);

        if (currentTaskListOptional.isPresent()) {
            TaskLists currentTaskList = currentTaskListOptional.get();
            updateTaskListFields(currentTaskList, updatedTaskList);
            taskListsRepository.save(currentTaskList);
        }

        return "redirect:/user/tasklist/list"; // Adicionando "user/" ao redirecionamento
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListsRepository.deleteById(id);
        return "redirect:/user/tasklist/list"; // Adicionando "user/" ao redirecionamento
    }

    // Método privado para atualizar os campos da lista de tarefas
    private void updateTaskListFields(TaskLists taskList, TaskLists updatedTaskList) {
        taskList.setName(updatedTaskList.getName());
        taskList.setColor(updatedTaskList.getColor());
        taskList.setUser(updatedTaskList.getUser());
    }
}
