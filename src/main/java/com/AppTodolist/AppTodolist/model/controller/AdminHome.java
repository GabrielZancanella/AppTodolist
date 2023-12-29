package com.AppTodolist.AppTodolist.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AppTodolist.AppTodolist.model.Tasks;
import com.AppTodolist.AppTodolist.model.UserRole;
import com.AppTodolist.AppTodolist.model.Users;
import com.AppTodolist.AppTodolist.repository.TaskListRepository;
import com.AppTodolist.AppTodolist.repository.TaskRepository;
import com.AppTodolist.AppTodolist.repository.UserRepository;

@Controller
@RequestMapping("/admin/")
public class AdminHome {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/home")
    public String showLoginForm(Model model) {
        List<Users> usuarios 	= userRepository.findAll();
        long totalUsuarios 		= usuarios.size();
        long totalAdmins   		= 0;
        long totalComuns   		= 0;

        for (Users user : usuarios) {
            // Verifica o papel do usuário
            if (user.getRole() == UserRole.USER.ADMIN) {
                totalAdmins++;
            } else if (user.getRole() == UserRole.USER.USER) {
                totalComuns++;
            }
        }
        
        long totalListas = taskListRepository.count();
        long outra 		= 0;
	    long Red 		= 0;
	    long Blue 		= 0;
	    long Yellow 	= 0;
	    long Green 		= 0;
	    long Purple		= 0;
	    long Pink 		= 0;
	    long Orange 	= 0;
	    long Brown		= 0;
	    long Grey 		= 0;
	    long Black 		= 0;
	    long White 		= 0;
	    long Turquoise 	= 0;
	    long Coral 		= 0;
	    long Gold 		= 0;
	    long Silver 	= 0;
        
        
        List<Tasks> tarefas 	= taskRepository.findAll();
        long totalTarefas 		= tarefas.size();
        long totalAfazer 		= 0;
        long totalEmProgresso 	= 0;
        long totalConcluida 	= 0;
        
        for (Tasks task : tarefas) {
            // Verifica o papel do usuário
            if ("A Fazer".equals(task.getStatus())) {
                totalAfazer++;
            } else if ("Em Progresso".equals(task.getStatus())) {
                totalEmProgresso++;
            } else if ("Concluído".equals(task.getStatus())) {
                totalConcluida++;
            }
        }

        model.addAttribute("totalUsuarios"   , totalUsuarios);
        model.addAttribute("totalAdmins"     , totalAdmins);
        model.addAttribute("totalComuns"     , totalComuns);
        model.addAttribute("totalListas"     , totalListas);
        model.addAttribute("totalTarefas"    , totalTarefas);
        model.addAttribute("totalAfazer"     , totalAfazer);
        model.addAttribute("totalEmProgresso", totalEmProgresso);
        model.addAttribute("totalConcluida"  , totalConcluida);

        return "/admin/index";
    }
}
