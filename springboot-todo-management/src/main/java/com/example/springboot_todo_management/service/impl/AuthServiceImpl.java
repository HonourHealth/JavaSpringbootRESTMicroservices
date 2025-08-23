package com.example.springboot_todo_management.service.impl;

import com.example.springboot_todo_management.dto.LoginDto;
import com.example.springboot_todo_management.dto.RegisterDto;
import com.example.springboot_todo_management.entity.Role;
import com.example.springboot_todo_management.entity.User;
import com.example.springboot_todo_management.exception.TodoAPIException;
import com.example.springboot_todo_management.repository.RoleRepository;
import com.example.springboot_todo_management.repository.UserRepository;
import com.example.springboot_todo_management.security.JwtTokenProvider;
import com.example.springboot_todo_management.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(registerDto.getUsername()))) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(registerDto.getEmail()))) {
            throw new TodoAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        User user = new User();
        user.setName(registerDto.getUsername());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return jwtTokenProvider.generateToken(authenticate);
    }
}
