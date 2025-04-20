package com.example.springboot_todo_management.repository;

import com.example.springboot_todo_management.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
