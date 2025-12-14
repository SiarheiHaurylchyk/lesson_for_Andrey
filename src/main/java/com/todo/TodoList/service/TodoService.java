package com.todo.TodoList.service;

import com.todo.TodoList.dto.TaskDto;
import com.todo.TodoList.dto.TodoDto;
import com.todo.TodoList.entity.Task;
import com.todo.TodoList.entity.TodoItem;
import com.todo.TodoList.mapper.TodoMapper;
import com.todo.TodoList.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TodoDto> getAllTodos() {
        List<TodoItem> allTodo = todoRepository.findAll();
        return allTodo.stream().map(todoMapper::toDto).toList();
    }

    public TodoDto getTodoById(Long id) {
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TodoItem not found with id: " + id));
        return todoMapper.toDto(todoItem);
    }

    @Transactional
    public TodoDto createTodo(TodoDto todoDto) {
        TodoItem todoItem = todoMapper.toEntity(todoDto);
        
        // Опционально создаем задачи, если они переданы в DTO
        if (todoDto.getTasks() != null && !todoDto.getTasks().isEmpty()) {
            for (TaskDto taskDto : todoDto.getTasks()) {
                Task task = todoMapper.toEntity(taskDto);
                todoItem.addTask(task);
            }
        }
        
        TodoItem saved = todoRepository.save(todoItem);
        return todoMapper.toDto(saved);
    }

    @Transactional
    public TodoDto updateTodo(Long id, TodoDto todoItemDto) {
        TodoItem existing = todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TodoItem not found with id: " + id));

        todoMapper.updateEntityFromDto(todoItemDto, existing);
        TodoItem saved = todoRepository.save(existing);
        return todoMapper.toDto(saved);
    }

    @Transactional
    public String deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new NoSuchElementException("TodoItem not found with id: " + id);
        }
        todoRepository.deleteById(id);
        return "Успешно удалено";
    }
}