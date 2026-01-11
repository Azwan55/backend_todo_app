package com.example.Backend_todo_list.services;

public class TodoUpdateRequest {

    private String title;
    private Boolean completed; // Boolean allows optional updates

    // getters & setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
