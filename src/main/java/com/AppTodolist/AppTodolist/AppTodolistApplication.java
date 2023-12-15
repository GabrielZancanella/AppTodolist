package com.AppTodolist.AppTodolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.AppTodolist.AppTodolist.model")
public class AppTodolistApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppTodolistApplication.class, args);
	}

}
