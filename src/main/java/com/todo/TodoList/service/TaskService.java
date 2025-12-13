package com.todo.TodoList.service;

import com.todo.TodoList.dto.TaskDto;
import com.todo.TodoList.entity.Task;
import com.todo.TodoList.entity.TodoItem;
import com.todo.TodoList.mapper.TodoMapper;
import com.todo.TodoList.repository.TaskRepository;
import com.todo.TodoList.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public List<TaskDto> getAllTasksForTodo(Long todoId, String sortBy, String direction) {
        verifyTodoExists(todoId);

        Sort.Direction dir = direction.equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(dir, sortBy);

        return taskRepository.findByTodoItemId(todoId, sort).stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto getTaskById(Long todoId, Long taskId) {
        Task task = findTaskByIdAndTodoId(todoId, taskId);
        return todoMapper.toDto(task);
    }

    @Transactional
    public TaskDto createTask(Long todoId, TaskDto taskDto) {
        TodoItem todoItem = todoRepository.findById(todoId)
                .orElseThrow(() -> new NoSuchElementException("TodoItem not found with id: " + todoId));

        Task task = todoMapper.toEntity(taskDto);
        task.setTodoItem(todoItem);

        Task saved = taskRepository.save(task);
        return todoMapper.toDto(saved);
    }

    @Transactional
    public TaskDto updateTask(Long todoId, Long taskId, TaskDto taskDto) {
        Task existing = findTaskByIdAndTodoId(todoId, taskId);

        todoMapper.updateTaskFromDto(taskDto, existing);

        Task saved = taskRepository.save(existing);
        return todoMapper.toDto(saved);
    }

    @Transactional
    public TaskDto toggleTaskCompletion(Long todoId, Long taskId) {
        Task task = findTaskByIdAndTodoId(todoId, taskId);
        task.setCompleted(!task.isCompleted());

        Task saved = taskRepository.save(task);
        return todoMapper.toDto(saved);
    }

    @Transactional
    public void deleteTask(Long todoId, Long taskId) {
        Task task = findTaskByIdAndTodoId(todoId, taskId);
        taskRepository.delete(task);
    }

    private void verifyTodoExists(Long todoId) {
        if (!todoRepository.existsById(todoId)) {
            throw new NoSuchElementException("TodoItem not found with id: " + todoId);
        }
    }

    private Task findTaskByIdAndTodoId(Long todoId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task not found with id: " + taskId));

        if (!task.getTodoItem().getId().equals(todoId)) {
            throw new NoSuchElementException(
                    "Task with id " + taskId + " does not belong to TodoItem with id " + todoId);
        }

        return task;
    }
}