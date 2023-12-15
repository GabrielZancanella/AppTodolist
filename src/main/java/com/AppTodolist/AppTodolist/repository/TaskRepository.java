package com.AppTodolist.AppTodolist.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.AppTodolist.AppTodolist.model.TaskLists;
import com.AppTodolist.AppTodolist.model.Tasks;

public interface TaskRepository extends CrudRepository<Tasks, Long> {
	Iterable<Tasks> findByTaskList(TaskLists TaskList);

	Tasks findById(Integer id);

	List<Tasks> findByName(String name);
	
	List<Tasks> findAll();
}