package com.example.Backend_todo_list.services;

public class TodoResponse {

    private Long id;
    private String title;
    private boolean completed;
    private String username;  // only username, no password

    public TodoResponse(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.completed = todo.isCompleted();
        this.username = todo.getUser().getUsername();
    }

    // getters only (optional setters if needed)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public String getUsername() { return username; }
}
