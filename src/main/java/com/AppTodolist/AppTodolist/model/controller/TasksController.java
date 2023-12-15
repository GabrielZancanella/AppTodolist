package com.AppTodolist.AppTodolist.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Tasks;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.TaskRepository;

@Controller
@RequestMapping("/task")
public class TasksController {

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
        return "task/add-task";
    }

    // Método POST para processar a adição de tarefas
    @PostMapping("/add")
    public String addTask(@ModelAttribute Tasks task) {
        taskRepository.save(task);
        return "redirect:/task/list";
    }

    // READ
    @GetMapping("/list")
    public String listTasks(Model model) {
        List<Tasks> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);

        return "task/tasklist";
    }

    // READ
    @GetMapping("/{id}")
    public String taskDetails(@PathVariable Long id, Model model) {
        Tasks task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return "redirect:/error";
        }

        model.addAttribute("task", task);
        return "task/taskDetails";
    }

    // UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Tasks task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return "redirect:/task/list";
        }

        model.addAttribute("updatedTask", task);
        return "task/update-task";
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

        return "redirect:/task/list";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/task/list";
    }
}
