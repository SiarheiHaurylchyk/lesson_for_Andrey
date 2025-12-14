package com.todo.TodoList.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Todo List API",
                version = "1.0.0",
                description = "RESTful API для управления списками дел (Todo Items) и задачами (Tasks). " +
                        "API предоставляет полный набор операций CRUD для работы со списками дел и их задачами.",
                contact = @Contact(
                        name = "Todo List Support",
                        email = "support@todolist.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Локальный сервер разработки"
                ),
                @Server(
                        url = "http://localhost:8080/api",
                        description = "Локальный сервер с базовым путем API"
                ),
                @Server(
                        url = "http://localhost:8080",
                        description = "Docker контейнер (localhost:8080)"
                )
        }
)
public class OpenApiConfig {
}
