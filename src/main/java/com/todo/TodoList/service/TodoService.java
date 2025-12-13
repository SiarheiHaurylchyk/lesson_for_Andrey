package com.todo.TodoList.service;

import com.todo.TodoList.dto.TodoDto;
import com.todo.TodoList.entity.TodoItem;
import com.todo.TodoList.mapper.TodoMapper;
import com.todo.TodoList.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TodoDto> getAllTodos(String sortBy, String direction) {
        Sort.Direction dir = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(dir, sortBy);

        return todoRepository.findAll(sort).stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    public TodoDto getTodoById(Long id) {
        TodoItem todoItem = todoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("TodoItem not found with id: " + id));
        return todoMapper.toDto(todoItem);
    }

    @Transactional
    public TodoDto createTodo(TodoDto todoDto) {
        TodoItem todoItem = todoMapper.toEntity(todoDto);
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
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new NoSuchElementException("TodoItem not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }
}