package com.todo.TodoList.config;

/**
 * Константы для Swagger документации.
 * Содержит переиспользуемые строковые константы для описаний API ответов.
 */
public final class SwaggerConstants {

    private SwaggerConstants() {
        // Утилитный класс
    }

    // Описания для успешных ответов
    public static final String SUCCESS_200_DESC = "Операция выполнена успешно";
    public static final String CREATED_201_DESC = "Ресурс успешно создан";
    public static final String NO_CONTENT_204_DESC = "Операция выполнена успешно (нет содержимого для возврата)";

    // Описания для ошибок
    public static final String BAD_REQUEST_400_DESC = "Некорректные данные запроса";
    public static final String BAD_REQUEST_ID_DESC = "Некорректный формат ID";
    public static final String BAD_REQUEST_DATA_DESC = "Некорректные данные запроса (например, пустой title или превышение лимита символов)";
    public static final String BAD_REQUEST_PARAMS_DESC = "Некорректные параметры запроса";

    public static final String NOT_FOUND_404_DESC = "Ресурс не найден";
    public static final String NOT_FOUND_TODO_DESC = "Список дел с указанным ID не найден";
    public static final String NOT_FOUND_TASK_DESC = "Задача или список дел с указанными ID не найдены";

    public static final String INTERNAL_ERROR_500_DESC = "Внутренняя ошибка сервера";
    public static final String INTERNAL_ERROR_CREATE_DESC = "Внутренняя ошибка сервера при создании";
    public static final String INTERNAL_ERROR_UPDATE_DESC = "Внутренняя ошибка сервера при обновлении";
    public static final String INTERNAL_ERROR_DELETE_DESC = "Внутренняя ошибка сервера при удалении";

    // Коды ответов
    public static final String CODE_200 = "200";
    public static final String CODE_201 = "201";
    public static final String CODE_204 = "204";
    public static final String CODE_400 = "400";
    public static final String CODE_404 = "404";
    public static final String CODE_500 = "500";
}
