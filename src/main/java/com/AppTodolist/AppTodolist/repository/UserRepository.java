package com.AppTodolist.AppTodolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppTodolist.AppTodolist.model.Users;

public interface UserRepository extends CrudRepository<Users, Long> {
	Users findByUsername(String username);

	Users findBySenha(String senha);

	List<Users> findAll();
}