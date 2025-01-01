package com.javaspringbootrest.springboot.mapper;


import com.javaspringbootrest.springboot.dto.UserDto;
import com.javaspringbootrest.springboot.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    @Test
    void testMapToUserDto() {
        User user = new User(1L, "John", "Doe", "john.doe@example.com");

        UserDto userDto = UserMapper.mapToUserDto(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    void testMapToUser() {
        UserDto userDto = new UserDto(1L, "John", "Doe", "john.doe@example.com");

        User user = UserMapper.mapToUser(userDto);

        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
    }
}
