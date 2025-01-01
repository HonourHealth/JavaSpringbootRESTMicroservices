package com.javaspringbootrest.springboot.mapper;

import com.javaspringbootrest.springboot.dto.UserDto;
import com.javaspringbootrest.springboot.entity.User;

public class UserMapper {
    //Convert User JPA Entity to UserDto
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    //Convert UserDto to User JPA Entity
    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );
    }
}
