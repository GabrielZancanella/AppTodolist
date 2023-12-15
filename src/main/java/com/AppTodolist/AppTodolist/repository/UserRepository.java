package com.AppTodolist.AppTodolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppTodolist.AppTodolist.model.UserRole;
import com.AppTodolist.AppTodolist.model.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
	Users findByUsername(String username);
	
	Users findByRole(UserRole userRole);

	Users findBySenha(String senha);

	List<Users> findAll();
}