package com.todo.TodoList.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskDto {
    private Long id;

    @NotBlank(message = "Task title cannot be empty")
    @Size(min = 1, max = 255, message = "Task title must be between 1 and 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    private boolean completed;
    private LocalDateTime createdAt;

    private Long todoItemId;
}