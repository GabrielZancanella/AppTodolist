package com.AppTodolist.AppTodolist.model;

public class userContext {

    private static final ThreadLocal<Users> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(Users user) {
        currentUser.set(user);
    }

    public static Users getCurrentUser() {
        return currentUser.get();
    }

    public static void clearCurrentUser() {
        currentUser.remove();
    }
}