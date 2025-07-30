package com.example.springboot_todo_management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class TodoAPIException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

}
