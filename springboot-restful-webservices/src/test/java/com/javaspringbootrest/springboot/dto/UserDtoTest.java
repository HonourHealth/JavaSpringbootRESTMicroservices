package com.javaspringbootrest.springboot.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDtoTest {

    @Test
    void create_user_dto_with_all_args_constructor() {
        // Create UserDto using the all-args constructor
        UserDto userDto = new UserDto(1L, "John", "Doe", "john.doe@email.com");

        // Assert that all fields are correctly initialized
        assertEquals(1L, userDto.getId());
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("john.doe@email.com", userDto.getEmail());
    }

    // Create UserDto with valid values for all fields
    @Test
    void create_user_dto_with_valid_values() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("john.doe@email.com");

        assertEquals(1L, userDto.getId());
        assertEquals("John", userDto.getFirstName());
        assertEquals("Doe", userDto.getLastName());
        assertEquals("john.doe@email.com", userDto.getEmail());
    }

    @Test
    void default_test_in_ykb() {
        UserDto userDto = new UserDto();
        userDto.setId(null);
        userDto.setEmail(null);
        userDto.setFirstName(null);
        userDto.setLastName(null);

        userDto.getId();
        userDto.getEmail();
        userDto.getFirstName();
        userDto.getLastName();

        assertNotNull(userDto);
    }
}