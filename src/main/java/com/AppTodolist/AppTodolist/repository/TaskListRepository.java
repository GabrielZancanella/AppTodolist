package com.AppTodolist.AppTodolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppTodolist.AppTodolist.model.TaskLists;

public interface TaskListRepository extends CrudRepository<TaskLists, Long> {
	List<TaskLists> findByUser_id(Long user_id);

	TaskLists findById(Integer id);

	List<TaskLists> findByName(String name);
	
	List<TaskLists> findAll();
}