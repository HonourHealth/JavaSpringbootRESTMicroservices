package com.example.springboot_todo_management.service;

import com.example.springboot_todo_management.dto.LoginDto;
import com.example.springboot_todo_management.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
