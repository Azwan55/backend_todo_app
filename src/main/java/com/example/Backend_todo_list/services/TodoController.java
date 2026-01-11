package com.example.Backend_todo_list.services;

import com.example.Backend_todo_list.repositories.TodoRepository;
import com.example.Backend_todo_list.repositories.UserRepository;
import com.example.Backend_todo_list.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    // GET all todos for current user
    @GetMapping
    public List<TodoResponse> getTodos(@AuthenticationPrincipal String username) {
        return todoRepository.findByUserUsername(username)
                .stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }

    // create
    @PostMapping
    public TodoResponse addTodo(@RequestBody Todo todo, @AuthenticationPrincipal String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        todo.setUser(user);
        Todo saved = todoRepository.save(todo);
        return new TodoResponse(saved);
    }

    // Update
    @PatchMapping("/{id}")
    public TodoResponse updateTodo(@PathVariable Long id,
                                   @RequestBody TodoUpdateRequest updateRequest,
                                   @AuthenticationPrincipal String username) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // Check ownership
        if (!todo.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access");
        }

        // Apply updates if present
        if (updateRequest.getTitle() != null) {
            todo.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getCompleted() != null) {
            todo.setCompleted(updateRequest.getCompleted());
        }

        Todo updated = todoRepository.save(todo);
        return new TodoResponse(updated);
    }


    @DeleteMapping("/{id}")
    public List<TodoResponse> deleteTodo(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName(); // always safe

        // Find the todo
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Record not found"
                ));

        // Check ownership
        if (!todo.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
        }

        // Delete
        todoRepository.delete(todo);

        // Return updated list
        return todoRepository.findByUserUsername(username)
                .stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }


}
