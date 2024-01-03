package com.AppTodolist.AppTodolist.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarefa") 
public class Tasks implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_list_id")
    private TaskLists taskList;

	@Column(name = "tarnom", nullable = false)
	private String name;

	@Column(name = "tardes", nullable = false)
	private String description;

	@Column(name = "tarinc")
	private LocalDateTime inclusion;
	
	@PrePersist
    public void prePersist() {
        if (inclusion == null) {
        	inclusion = LocalDateTime.now();
        }
    }
	
	@Column(name = "tarest", nullable = false)
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaskLists getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskLists tasklist) {
		this.taskList = tasklist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getInclusion() {
		return inclusion;
	}

	public void setInclusion(LocalDateTime inclusion) {
		this.inclusion = inclusion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Tasks [id=" + id + ", taskList=" + taskList + ", name=" + name + ", description=" + description
				+ ", inclusion=" + inclusion + ", status=" + status + "]";
	}
}
