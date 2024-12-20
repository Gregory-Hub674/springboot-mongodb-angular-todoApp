package com.example.todoapp.controllers;

import javax.validation.Valid;
import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
//import java.util.logging.Logger;

import org.bson.types.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    // Get all tasks
    @GetMapping("/tasks")
    public List<Todo> getAllTodos() {
        Sort sortByCreatedAtDesc = Sort.by(Sort.Direction.DESC, "createdAt");
        return todoRepository.findAll(sortByCreatedAtDesc);
    }

    // Add a new task (incompleted task)
    @PostMapping("/tasks")
    public Todo createTodo(@Valid @RequestBody Todo todo) throws JsonProcessingException {
        todo.setCompleted(false);
        todo.setId(null);
        return todoRepository.save(todo);
    }

    // Find a task by id
    @GetMapping(value="/tasks/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") ObjectId id) {
        return todoRepository.findById(id)
                .map(todo -> ResponseEntity.ok().body(todo))
                .orElse(ResponseEntity.notFound().build());
    }

    // Update task by id
    @PutMapping(value="/tasks/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") ObjectId id,
    @Valid @RequestBody Todo todo) {
        return todoRepository.findById(id)
                .map(todoData -> {
                    todoData.setTitle(todo.getTitle());
                    todoData.setCompleted(todo.getCompleted());
                    Todo updatedTodo = todoRepository.save(todoData);
                    return ResponseEntity.ok().body(updatedTodo);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Delete task by id
    @DeleteMapping(value="/tasks/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") ObjectId _id) {
        return todoRepository.findById(_id)
                .map(todo -> {
                    todoRepository.deleteById(_id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}