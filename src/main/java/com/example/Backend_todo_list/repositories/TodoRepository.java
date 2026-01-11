package com.example.Backend_todo_list.repositories;

import com.example.Backend_todo_list.services.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserUsername(String username);
}

