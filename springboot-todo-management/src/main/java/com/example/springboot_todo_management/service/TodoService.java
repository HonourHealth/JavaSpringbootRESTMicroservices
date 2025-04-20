package com.example.springboot_todo_management.service;

import com.example.springboot_todo_management.dto.TodoDto;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
}
