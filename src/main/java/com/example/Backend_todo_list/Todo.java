package com.example.Backend_todo_list;

import com.example.Backend_todo_list.user.model.User;
import jakarta.persistence.*;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

