package com.AppTodolist.AppTodolist.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario") 
public class Users implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TaskLists> taskLists;

	@Column(name = "usuema", nullable = false)
	private String email;

	@Column(name = "ususen", nullable = false)
	private String senha;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<TaskLists> getTaskLists() {
		return taskLists;
	}

	public void setTaskLists(List<TaskLists> taskLists) {
		this.taskLists = taskLists;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return username + ", " + email + ", " + senha ;
	}	
	
}
