package com.AppTodolist.AppTodolist.model;

public class UserTaskDto {
    private Users user;
    private String taskName;

    public UserTaskDto(Users user, String taskName) {
        this.user = user;
        this.taskName = taskName;
    }

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}