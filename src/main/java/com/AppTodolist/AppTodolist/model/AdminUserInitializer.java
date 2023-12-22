package com.AppTodolist.AppTodolist.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.AppTodolist.AppTodolist.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AdminUserInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initializeAdminUser() {
        // Busca todos os usuários com a função findByRole
        List<Users> adminUsers = userRepository.findByRole(UserRole.ADMIN);

        // Se não houver nenhum usuário administrador, inicializa um
        if (adminUsers.isEmpty()) {
            Users adminUser = new Users();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setSenha("admin"); // Configure a senha diretamente como texto

            userRepository.save(adminUser);
        }
    }
}