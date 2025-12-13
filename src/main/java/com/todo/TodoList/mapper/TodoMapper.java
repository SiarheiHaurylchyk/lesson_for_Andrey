package com.todo.TodoList.mapper;

import com.todo.TodoList.dto.TaskDto;
import com.todo.TodoList.dto.TodoDto;
import com.todo.TodoList.entity.Task;
import com.todo.TodoList.entity.TodoItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoDto toDto(TodoItem todoItem);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TodoItem toEntity(TodoDto todoItemDto);

    @Mapping(target = "todoItemId", source = "todoItem.id")
    TaskDto toDto(Task task);

    @Mapping(target = "todoItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(TaskDto taskDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(TodoDto dto, @MappingTarget TodoItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "todoItem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateTaskFromDto(TaskDto dto, @MappingTarget Task entity);
}