package com.javaspringbootrest.springboot.controller;

import com.javaspringbootrest.springboot.dto.UserDto;
import com.javaspringbootrest.springboot.service.UserService;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    void createUser_test() {
        @Valid UserDto user = new UserDto();
        UserDto dto = new UserDto();
        when(userService.createUser(user)).thenReturn(dto);
        ResponseEntity<UserDto> response = userController.createUser(user);
        assertNotNull(response);
        assertEquals(dto, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getUserById_test() {
        Long userId = 1L;
        UserDto user = new UserDto();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        when(userService.getUserById(userId)).thenReturn(user);
        ResponseEntity<UserDto> response = userController.getUserById(userId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void getAllUsers_test() {
        List<UserDto> users = new ArrayList<>();
        UserDto user1 = new UserDto();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("johndoe@example.com");
        users.add(user1);
        UserDto user2 = new UserDto();
        user2.setId(2L);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setEmail("janedoe@example.com");
        users.add(user2);
        when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity<List<UserDto>> response = userController.getAllUsers();
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    void updateUser_test() {
        Long userId = 1L;
        UserDto user = new UserDto();
        user.setFirstName("John Doe");
        user.setLastName("John Doe");
        user.setEmail("johndoe@example.com");
        UserDto updatedUser = new UserDto();
        updatedUser.setId(userId);
        updatedUser.setFirstName("John Doe");
        updatedUser.setLastName("John Doe");
        updatedUser.setEmail("johndoe@example.com");
        when(userService.updateUser(user)).thenReturn(updatedUser);
        ResponseEntity<UserDto> response = userController.updateUser(userId, user);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }

    @Test
    void deleteUser_test() {
        Long userId = 1L;
        doNothing().when(userService).deleteUser(userId);
        ResponseEntity<String> response = userController.deleteUser(userId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());
    }
}