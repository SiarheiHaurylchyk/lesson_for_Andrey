package com.todo.TodoList.repository;


import com.todo.TodoList.entity.Task;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTodoItemId(Long todoItemId);
    
    @Query("SELECT t FROM Task t WHERE t.id = :taskId AND t.todoItem.id = :todoId")
    Optional<Task> findByIdAndTodoItemId(@Param("taskId") Long taskId, @Param("todoId") Long todoId);
}