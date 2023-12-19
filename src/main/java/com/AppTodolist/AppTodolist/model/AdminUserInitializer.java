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
        Users adminUser = userRepository.findByRole(UserRole.ADMIN);

        if (adminUser == null) {
            adminUser = new Users();
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@example.com");
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setSenha("admin"); // Configure a senha diretamente como texto

            userRepository.save(adminUser);
        }
    }
}
