package com.example.springboot_todo_management.controller;

import com.example.springboot_todo_management.dto.TodoDto;
import com.example.springboot_todo_management.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
public class TodoController {
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto) {
        TodoDto savedTodoDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable("id") Long todoId) {
        TodoDto todoDto = todoService.getTodoById(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        List<TodoDto> todoDtos = todoService.getAllTodos();
        return ResponseEntity.ok(todoDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId) {
        TodoDto updatedTodoDto = todoService.updateTodo(todoDto, todoId);
        return new ResponseEntity<>(updatedTodoDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable("id") Long todoId) {
        todoService.deleteTodoById(todoId);
        return ResponseEntity.ok("Todo with id " + todoId + " deleted successfully.");
    }


    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodoById(@PathVariable("id") Long todoId) {
        TodoDto completedTodoDto = todoService.completeTodoById(todoId);
        return new ResponseEntity<>(completedTodoDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDto> incompleteTodoById(@PathVariable("id") Long todoId) {
        TodoDto incompleteTodoDto = todoService.incompleteTodoById(todoId);
        return new ResponseEntity<>(incompleteTodoDto, HttpStatus.OK);
    }
}
