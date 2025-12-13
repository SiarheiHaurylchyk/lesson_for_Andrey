package com.todo.TodoList.controller;

import com.todo.TodoList.dto.TodoDto;
import com.todo.TodoList.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoItemService;

    @GetMapping("/todos/{sortBy}/{direction}")
    public ResponseEntity<List<TodoDto>> getAllTodos(
            @PathVariable(required = false) String sortBy){
        return ResponseEntity.ok(todoItemService.getAllTodos(sortBy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoItemService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody TodoDto todoItemDto) {
        TodoDto created = todoItemService.createTodo(todoItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoDto todoItemDto) {
        return ResponseEntity.ok(todoItemService.updateTodo(id, todoItemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoItemService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}