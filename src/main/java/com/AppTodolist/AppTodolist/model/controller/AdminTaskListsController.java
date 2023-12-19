package com.AppTodolist.AppTodolist.model.controller;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/admin/tasklist")
public class AdminTaskListsController {

    @Autowired
    private TaskListRepository taskListsRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String showAddTaskListForm(Model model) {
    	Users user = userRepository.findAllById(1);
	    model.addAttribute("user", user);

        model.addAttribute("taskList", new TaskLists());
        return "admin/tasklist/add-tasklist";
    }

    @PostMapping("/add")
    public String addTaskList(@ModelAttribute TaskLists taskList, Principal principal) {
        taskListsRepository.save(taskList);
        return "redirect:/admin/tasklist/list";
    }

    @GetMapping("/list")
    public String listTaskLists(Model model, Principal principal) {
        List<TaskLists> taskLists = null;
        taskLists = taskListsRepository.findAll();

        /*if (principal != null) {
            String username = principal.getName();
            Users user = userRepository.findByUsername(username);
            taskLists = taskListsRepository.findByUser_id(user.getId());
        }
		*/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (taskLists != null) {
            taskLists.forEach(taskList -> {
                if (taskList.getCreateDate() != null) {
                    taskList.setFormattedCreateDate(taskList.getCreateDate().format(formatter));
                }
            });
        }

        model.addAttribute("taskLists", taskLists);

        return "admin/tasklist/tasklist_list";
    }

    @GetMapping("/{id}")
    public String taskListDetails(@PathVariable Long id, Model model) {
        TaskLists taskList = taskListsRepository.findById(id).orElse(null);

        if (taskList == null) {
            return "redirect:/admin/error";
        }

        model.addAttribute("taskList", taskList);
        return "admin/tasklist/taskListDetails";
    }

    @GetMapping("/update/{id}")
    public String showUpdateTaskListForm(@PathVariable Long id, Model model) {
        TaskLists taskList = taskListsRepository.findById(id).orElse(null);

        if (taskList == null) {
            return "redirect:/admin/error";
        }

        model.addAttribute("updatedTaskList", taskList);
        return "admin/tasklist/update-tasklist";
    }

    @PostMapping("/update/{id}")
    public String updateTaskList(@PathVariable Long id, @ModelAttribute TaskLists updatedTaskList) {
        Optional<TaskLists> currentTaskList = taskListsRepository.findById(id);

        if (currentTaskList.isPresent()) {
            TaskLists taskList = currentTaskList.get();
            updateTaskListFields(taskList, updatedTaskList);
            taskListsRepository.save(taskList);
        }

        return "redirect:/admin/tasklist/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListsRepository.deleteById(id);
        return "redirect:/admin/tasklist/list";
    }

    private void updateTaskListFields(TaskLists taskList, TaskLists updatedTaskList) {
        taskList.setName(updatedTaskList.getName());
        taskList.setColor(updatedTaskList.getColor());
        taskList.setTasks(updatedTaskList.getTasks());
    }
}
