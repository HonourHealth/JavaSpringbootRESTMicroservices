package com.example.springboot_todo_management.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {
    public static void main(String[] args) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(
            "Encoded password for 'user': " + passwordEncoder.encode("user")
        );

        System.out.println(
            "Encoded password for 'admin': " + passwordEncoder.encode("admin")
        );

    }
}
