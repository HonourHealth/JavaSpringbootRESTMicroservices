package com.example.employeeservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @InjectMocks
    MessageController messageController;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(messageController, "message", "Hello, World!");
    }

    @Test
    void get_message_when_property_not_defined() {
        ReflectionTestUtils.setField(messageController, "message", null);
        String result = messageController.message();
        assertNull(result, "Expected message to be null when property is not defined");
    }

    @Test
    void get_message_when_property_is_defined() {
        ReflectionTestUtils.setField(messageController, "message", "Hello, World!");
        String result = messageController.message();
        assertEquals("Hello, World!", result, "Expected message to match the defined property");
    }

    @Test
    void get_message_when_property_is_empty() {
        ReflectionTestUtils.setField(messageController, "message", "");
        String result = messageController.message();
        assertEquals("", result, "Expected message to be empty when property is empty");
    }

}