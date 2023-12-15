package com.AppTodolist.AppTodolist.model.controller;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasklist")
public class TaskListsController {

    @Autowired
    private TaskListRepository taskListsRepository;

    // CREATE
    @GetMapping("/add")
    public String showAddTaskListForm(Model model) {
        model.addAttribute("taskList", new TaskLists());
        return "tasklist/add-tasklist";
    }

    // Método POST para processar a adição de lista de tarefas
    @PostMapping("/add")
    public String addTaskList(@ModelAttribute TaskLists taskList) {
        taskListsRepository.save(taskList);
        return "redirect:/tasklist/list";
    }

    // READ
    @GetMapping("/list")
    public String listTaskLists(Model model) {
        List<TaskLists> taskLists = taskListsRepository.findAll();
        model.addAttribute("taskLists", taskLists);
        return "tasklist/tasklist_list";
    }

    // READ
    @GetMapping("/{id}")
    public String taskListDetails(@PathVariable Long id, Model model) {
        TaskLists taskList = taskListsRepository.findById(id).orElse(null);

        if (taskList == null) {
            return "redirect:/error";
        }

        model.addAttribute("taskList", taskList);
        return "tasklist/taskListDetails";
    }

    // UPDATE
    @GetMapping("/update/{id}")
    public String showUpdateTaskListForm(@PathVariable Long id, Model model) {
        TaskLists taskList = taskListsRepository.findById(id).orElse(null);

        if (taskList == null) {
            return "redirect:/error";
        }

        model.addAttribute("updatedTaskList", taskList);
        return "tasklist/update-tasklist";
    }

    // POST para processar a atualização da lista de tarefas
    @PostMapping("/update/{id}")
    public String updateTaskList(@PathVariable Long id, @ModelAttribute TaskLists updatedTaskList) {
        Optional<TaskLists> currentTaskList = taskListsRepository.findById(id);

        if (currentTaskList.isPresent()) {
            TaskLists taskList = currentTaskList.get();
            updateTaskListFields(taskList, updatedTaskList);
            taskListsRepository.save(taskList);
        }

        return "redirect:/tasklist/list";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListsRepository.deleteById(id);
        return "redirect:/tasklist/list";
    }

    // Método privado para atualizar os campos da lista de tarefas
    private void updateTaskListFields(TaskLists taskList, TaskLists updatedTaskList) {
        taskList.setName(updatedTaskList.getName());
        taskList.setColor(updatedTaskList.getColor());
        taskList.setTasks(updatedTaskList.getTasks());
    }
}
