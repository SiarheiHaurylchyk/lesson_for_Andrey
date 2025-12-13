package com.todo.TodoList.controller;

import com.todo.TodoList.dto.TaskDto;
import com.todo.TodoList.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/todos/{todoId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasksForTodo(
            @PathVariable Long todoId,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String direction) {
        return ResponseEntity.ok(taskService.getAllTasksForTodo(todoId, sortBy, direction));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(
            @PathVariable Long todoId,
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(todoId, taskId));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @PathVariable Long todoId,
            @Valid @RequestBody TaskDto taskDto) {
        TaskDto created = taskService.createTask(todoId, taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long todoId,
            @PathVariable Long taskId,
            @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(todoId, taskId, taskDto));
    }

    @PatchMapping("/{taskId}/toggle")
    public ResponseEntity<TaskDto> toggleTaskCompletion(
            @PathVariable Long todoId,
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.toggleTaskCompletion(todoId, taskId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long todoId,
            @PathVariable Long taskId) {
        taskService.deleteTask(todoId, taskId);
        return ResponseEntity.noContent().build();
    }
}