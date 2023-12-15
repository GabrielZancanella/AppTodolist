package com.AppTodolist.AppTodolist.model.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Tasks;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.TaskRepository;

@Controller
@RequestMapping("/admin/task") // Adicionando "admin/" ao mapeamento
public class AdminTasksController {

    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private TaskListRepository taskListRepository;

    // CREATE
    @GetMapping("/add")
    public String showAddTaskForm(Model model) {
        List<TaskLists> taskList = taskListRepository.findAll();
        model.addAttribute("taskList", taskList);

        model.addAttribute("task", new Tasks());
        return "admin/task/add-task"; // Adicionando "admin/" ao redirecionamento
    }

    // Método POST para processar a adição de tarefas
    @PostMapping("/add")
    public String addTask(@ModelAttribute Tasks task) {
        taskRepository.save(task);
        return "redirect:/admin/task/list"; // Adicionando "admin/" ao redirecionamento
    }

    // READ
    @GetMapping("/list")
    public String listTasks(Model model) {
        List<Tasks> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);

        return "admin/task/tasklist"; // Adicionando "admin/" ao redirecionamento
    }

    // READ
    @GetMapping("/{id}")
    public String taskDetails(@PathVariable Long id, Model model) {
        Tasks task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return "redirect:/admin/error"; // Adicionando "admin/" ao redirecionamento
        }
        
        if (task.getInclusion() != null) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        String formattedDate = task.getInclusion().format(formatter);
	        model.addAttribute("formattedDate", formattedDate);
        } else {
            model.addAttribute("formattedDate", "00/00/0000 00:00");
        }
        
        model.addAttribute("task", task);
        return "admin/task/taskDetails"; // Adicionando "admin/" ao redirecionamento
    }

    // UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Tasks task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return "redirect:/admin/task/list"; // Adicionando "admin/" ao redirecionamento
        }

        model.addAttribute("updatedTask", task);
        return "admin/task/update-task"; // Adicionando "admin/" ao redirecionamento
    }

    // UPDATE
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Tasks updatedTask) {
        Tasks task = taskRepository.findById(id).orElse(null);

        if (task != null) {
            task.setName(updatedTask.getName());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setTaskList(updatedTask.getTaskList());

            taskRepository.save(task);
        }

        return "redirect:/admin/task/list"; // Adicionando "admin/" ao redirecionamento
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/admin/task/list"; // Adicionando "admin/" ao redirecionamento
    }
}
