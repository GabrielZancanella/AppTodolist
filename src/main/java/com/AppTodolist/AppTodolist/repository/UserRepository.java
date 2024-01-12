package com.AppTodolist.AppTodolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppTodolist.AppTodolist.model.UserRole;
import com.AppTodolist.AppTodolist.model.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
	long count();
	
	Users findByUsername(String username);
	
	List<Users> findByUsernameAndSenha(String username, String senha);
	
	List<Users> findByRole(UserRole role);

	Users findBySenha(String senha);

	List<Users> findAll();
}