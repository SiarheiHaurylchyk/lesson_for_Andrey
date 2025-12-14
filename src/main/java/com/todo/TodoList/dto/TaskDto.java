package com.todo.TodoList.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "DTO для представления задачи (Task)")
public class TaskDto {
    @Schema(description = "Уникальный идентификатор задачи", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Название задачи", example = "Купить молоко", required = true, minLength = 1, maxLength = 255)
    @NotBlank(message = "Task title cannot be empty")
    @Size(min = 1, max = 255, message = "Task title must be between 1 and 255 characters")
    private String title;

    @Schema(description = "Описание задачи", example = "Купить 2 литра молока в магазине", maxLength = 1000)
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Schema(description = "Статус выполнения задачи", example = "false")
    private boolean completed;

    @Schema(description = "Дата и время создания задачи", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Идентификатор списка дел, к которому принадлежит задача", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long todoItemId;
}