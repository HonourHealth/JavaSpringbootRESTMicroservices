package com.javaspringbootrest.springboot.service.impl;

import com.javaspringbootrest.springboot.dto.UserDto;
import com.javaspringbootrest.springboot.entity.User;
import com.javaspringbootrest.springboot.exception.EmailAlreadyExistException;
import com.javaspringbootrest.springboot.exception.ResourceNotFoundException;
import com.javaspringbootrest.springboot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserRepository userRepository;

    @Test
    void createUser_test() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("FirstName");
        userDto.setLastName("LastName");
        userDto.setId(1L);
        userDto.setEmail("email@email.com");

        User user = new User();
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("email@email.com");
        user.setId(1L);

        Optional<User> t = Optional.empty();
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(t);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto response = userService.createUser(userDto);
        assertNotNull(response);
        assertEquals("FirstName", response.getFirstName());
        assertEquals("LastName", response.getLastName());
        assertEquals("email@email.com", response.getEmail());
    }

    @Test
    void createUser_withExistingEmail_throwsEmailAlreadyExistException() {
        UserDto userDto = new UserDto();
        userDto.setEmail("email@email.com");

        User user = new User();
        user.setEmail("email@email.com");

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistException.class, () -> userService.createUser(userDto));
    }

    @Test
    void getUserById_withExistingId_returnsUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("email@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(1L);

        assertNotNull(userDto);
    }

    @Test
    void getUserById_withNonExistingId_throwsResourceNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getAllUsers_test() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("FirstName1");
        user1.setLastName("LastName1");
        user1.setEmail("email1@email.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("FirstName2");
        user2.setLastName("LastName2");
        user2.setEmail("email2@email.com");

        userList.add(user1);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);
        assertNotNull(userService.getAllUsers());
    }

    @Test
    void updateUser_withValidData_returnsUpdatedUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("UpdatedFirstName");
        userDto.setLastName("UpdatedLastName");
        userDto.setEmail("updated@email.com");

        User user = new User();
        user.setId(1L);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setEmail("email@email.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto);

        assertNotNull(updatedUser);
        assertEquals("UpdatedFirstName", updatedUser.getFirstName());
        assertEquals("UpdatedLastName", updatedUser.getLastName());
        assertEquals("updated@email.com", updatedUser.getEmail());
    }

    @Test
    void updateUser_withNonExistingId_throwsResourceNotFoundException() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userDto));
    }

    @Test
    void deleteUser_withExistingId_deletesUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
    }

    @Test
    void deleteUser_withNonExistingId_throwsResourceNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }
}