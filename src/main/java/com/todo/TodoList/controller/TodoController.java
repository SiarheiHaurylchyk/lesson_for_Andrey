package com.todo.TodoList.controller;

import com.todo.TodoList.dto.TodoDto;
import com.todo.TodoList.service.TodoService;
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
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Tag(name = "Todo Controller", description = "API для управления списками дел (Todo Items)")
public class TodoController {

    private final TodoService todoItemService;

    @GetMapping("/todosAll")
    @Operation(
            summary = "Получить все списки дел",
            description = "Возвращает список всех Todo Items. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные параметры запроса",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content
            )
    })
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        return ResponseEntity.ok(todoItemService.getAllTodos());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить список дел по ID",
            description = "Возвращает конкретный Todo Item по его уникальному идентификатору. " +
                    "Включает все связанные задачи (tasks), если они есть."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список дел успешно найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список дел с указанным ID не найден",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный формат ID",
                    content = @Content
            )
    })
    public ResponseEntity<TodoDto> getTodoById(
            @Parameter(
                    description = "Уникальный идентификатор списка дел",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {
        return ResponseEntity.ok(todoItemService.getTodoById(id));
    }

    @PostMapping
    @Operation(
            summary = "Создать новый список дел",
            description = "Создает новый Todo Item. Обязательные поля: title (1-255 символов). " +
                    "Опциональные поля: description (до 1000 символов), tasks (список задач). " +
                    "Поле completed по умолчанию устанавливается в false. " +
                    "Если переданы tasks, они будут созданы вместе со списком дел."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Список дел успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса (например, пустой title или превышение лимита символов)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера при создании",
                    content = @Content
            )
    })
    public ResponseEntity<TodoDto> createTodo(
            @Parameter(
                    description = "Данные для создания нового списка дел",
                    required = true
            )
            @Valid @RequestBody TodoDto todoItemDto) {
        TodoDto created = todoItemService.createTodo(todoItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Обновить список дел",
            description = "Полностью обновляет существующий Todo Item по его ID. " +
                    "Все поля должны быть предоставлены в теле запроса. " +
                    "Если поле не указано, оно будет установлено в значение по умолчанию."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список дел успешно обновлен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные запроса",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список дел с указанным ID не найден",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера при обновлении",
                    content = @Content
            )
    })
    public ResponseEntity<TodoDto> updateTodo(
            @Parameter(
                    description = "Уникальный идентификатор списка дел для обновления",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Parameter(
                    description = "Обновленные данные списка дел",
                    required = true
            )
            @Valid @RequestBody TodoDto todoItemDto) {
        return ResponseEntity.ok(todoItemService.updateTodo(id, todoItemDto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удалить список дел",
            description = "Удаляет Todo Item по его ID. " +
                    "При удалении списка дел также удаляются все связанные с ним задачи (tasks). " +
                    "Операция необратима."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Список дел успешно удален (нет содержимого для возврата)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Список дел с указанным ID не найден",
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
    public ResponseEntity<String> deleteTodo(
            @Parameter(
                    description = "Уникальный идентификатор списка дел для удаления",
                    example = "1",
                    required = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok( todoItemService.deleteTodo(id));
    }
}