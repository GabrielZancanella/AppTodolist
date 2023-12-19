package com.AppTodolist.AppTodolist.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "lista_tarefa") 
public class TaskLists implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tasks> tasks;

    @Column(name = "listardatcri")
    private LocalDateTime createDate;

    @PrePersist
    public void prePersist() {
        if (createDate == null) {
            createDate = LocalDateTime.now();
        }
    }
	
	@Transient
	private String formattedCreateDate;

	@Column(name = "listarcor", nullable = false)
	private String color;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Users getuser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Tasks> getTasks() {
		return tasks;
	}

	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Lista: "+ name + " = " + color + "\n"+ tasks;
	}

	public String getFormattedCreateDate() {
		return formattedCreateDate;
	}

	public void setFormattedCreateDate(String formattedCreateDate) {
		this.formattedCreateDate = formattedCreateDate;
	}
}
