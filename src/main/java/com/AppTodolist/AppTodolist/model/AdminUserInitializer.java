package com.AppTodolist.AppTodolist.model;

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
        // Verifica se há um usuário admin no banco de dados
        Users adminUser = userRepository.findByRole(UserRole.ADMIN);

        if (adminUser == null) {
            // Se não houver, cria um usuário admin
            adminUser = new Users();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setSenha("admin");
            adminUser.setRole(UserRole.ADMIN);

            userRepository.save(adminUser);
        }
    }
}