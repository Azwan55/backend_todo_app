package com.example.Backend_todo_list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getTodos(Authentication auth) {
        return todoRepository.findByUserUsername(auth.getName());
    }

    @PostMapping
    public Todo addTodo(@RequestBody Todo todo, Authentication auth) {
        // set user here
        return todoRepository.save(todo);
    }
}

