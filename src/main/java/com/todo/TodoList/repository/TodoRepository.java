package com.todo.TodoList.repository;

import com.todo.TodoList.entity.TodoItem;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<TodoItem, Long> {
    @EntityGraph(attributePaths = "tasks")
    @Override
    List<TodoItem> findAll(Sort sort);
    
    @EntityGraph(attributePaths = "tasks")
    @Override
    Optional<TodoItem> findById(Long id);
}