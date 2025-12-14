package com.todo.TodoList.controller;

import com.todo.TodoList.dto.TaskDto;
import com.todo.TodoList.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Task Controller", description = "API для управления задачами (Tasks) в рамках списков дел")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(
            summary = "Получить все задачи для списка дел",
            description = "Возвращает список всех задач (Tasks), принадлежащих указанному Todo Item. " +
                    "Задачи возвращаются в том порядке, в котором они были созданы."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список задач успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список дел с указанным ID не найден",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный формат ID списка дел",
                    content = @Content
            )
    })
    public ResponseEntity<List<TaskDto>> getAllTasksForTodo(
            @Parameter(
                    description = "Уникальный идентификатор списка дел",
                    example = "1",
                    required = true
            )
            @PathVariable Long todoId){
        return ResponseEntity.ok(taskService.getAllTasksForTodo(todoId));
    }

    @GetMapping("/{taskId}")
    @Operation(
            summary = "Получить задачу по ID",
            description = "Возвращает конкретную задачу по её уникальному идентификатору. " +
                    "Задача должна принадлежать указанному списку дел (todoId)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Задача успешно найдена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задача или список дел с указанными ID не найдены",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный формат ID",
                    content = @Content
            )
    })
    public ResponseEntity<TaskDto> getTaskById(
            @Parameter(
                    description = "Уникальный идентификатор списка дел",
                    example = "1",
                    required = true
            )
            @PathVariable Long todoId,
            @Parameter(
                    description = "Уникальный идентификатор задачи",
                    example = "1",
                    required = true
            )
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(todoId, taskId));
    }

    @PostMapping
    @Operation(
            summary = "Создать новую задачу",
            description = "Создает новую задачу в рамках указанного списка дел. " +
                    "Обязательные поля: title (1-255 символов). " +
                    "Опциональные поля: description (до 1000 символов). " +
                    "Поле completed по умолчанию устанавливается в false. " +
                    "Поле todoItemId автоматически устанавливается на основе todoId из пути."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Задача успешно создана",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса (например, пустой title или превышение лимита символов)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список дел с указанным ID не найден",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера при создании",
                    content = @Content
            )
    })
    public ResponseEntity<TaskDto> createTask(
            @Parameter(
                    description = "Уникальный идентификатор списка дел, к которому будет привязана задача",
                    example = "1",
                    required = true
            )
            @PathVariable Long todoId,
            @Parameter(
                    description = "Данные для создания новой задачи",
                    required = true
            )
            @Valid @RequestBody TaskDto taskDto) {
        TaskDto created = taskService.createTask(todoId, taskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{taskId}")
    @Operation(
            summary = "Обновить задачу",
            description = "Полностью обновляет существующую задачу по её ID. " +
                    "Задача должна принадлежать указанному списку дел (todoId). " +
                    "Все поля должны быть предоставлены в теле запроса."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Задача успешно обновлена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задача или список дел с указанными ID не найдены",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера при обновлении",
                    content = @Content
            )
    })
    public ResponseEntity<TaskDto> updateTask(
            @Parameter(
                    description = "Уникальный идентификатор списка дел",
                    example = "1",
                    required = true
            )
            @PathVariable Long todoId,
            @Parameter(
                    description = "Уникальный идентификатор задачи для обновления",
                    example = "1",
                    required = true
            )
            @PathVariable Long taskId,
            @Parameter(
                    description = "Обновленные данные задачи",
                    required = true
            )
            @Valid @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(todoId, taskId, taskDto));
    }

    @PatchMapping("/{taskId}/toggle")
    @Operation(
            summary = "Переключить статус выполнения задачи",
            description = "Переключает статус выполнения задачи (completed) между true и false. " +
                    "Если задача была выполнена (completed = true), она становится невыполненной (completed = false) и наоборот. " +
                    "Это частичное обновление, изменяется только поле completed."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Статус выполнения задачи успешно переключен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задача или список дел с указанными ID не найдены",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный формат ID",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера при обновлении",
                    content = @Content
            )
    })
    public ResponseEntity<TaskDto> toggleTaskCompletion(
            @Parameter(
                    description = "Уникальный идентификатор списка дел",
                    example = "1",
                    required = true
            )
            @PathVariable Long todoId,
            @Parameter(
                    description = "Уникальный идентификатор задачи для переключения статуса",
                    example = "1",
                    required = true
            )
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.toggleTaskCompletion(todoId, taskId));
    }

    @DeleteMapping("/{taskId}")
    @Operation(
            summary = "Удалить задачу",
            description = "Удаляет задачу по её уникальному идентификатору. " +
                    "Задача должна принадлежать указанному списку дел (todoId). " +
                    "Операция необратима."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Задача успешно удалена (нет содержимого для возврата)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Задача или список дел с указанными ID не найдены",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный формат ID",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера при удалении",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(
                    description = "Уникальный идентификатор списка дел",
                    example = "1",
                    required = true
            )
            @PathVariable Long todoId,
            @Parameter(
                    description = "Уникальный идентификатор задачи для удаления",
                    example = "1",
                    required = true
            )
            @PathVariable Long taskId) {
        taskService.deleteTask(todoId, taskId);
        return ResponseEntity.noContent().build();
    }
}