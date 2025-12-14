package com.todo.TodoList.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "DTO для представления списка дел (Todo Item)")
public class TodoDto {
    @Schema(description = "Уникальный идентификатор списка дел", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Название списка дел", example = "Покупки на неделю", required = true, minLength = 1, maxLength = 255)
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Schema(description = "Описание списка дел", example = "Список продуктов и товаров для покупки", maxLength = 1000)
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Schema(description = "Статус выполнения списка дел", example = "false")
    private boolean completed;

    @Schema(description = "Дата и время создания списка дел", example = "2024-01-15T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Список задач, принадлежащих данному списку дел. Опционально при создании - если переданы, будут созданы вместе со списком дел.")
    private List<TaskDto> tasks;
}