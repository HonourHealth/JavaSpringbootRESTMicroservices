package com.example.employeemanagementsystem.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "errorMessage";

        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionInheritance() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test message");
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void testResponseStatusAnnotation() {

        Class<ResourceNotFoundException> clazz = ResourceNotFoundException.class;

        ResponseStatus annotation = clazz.getAnnotation(ResponseStatus.class);

        assertNotNull(annotation, "ResponseStatus annotation should be present");
        assertEquals(HttpStatus.NOT_FOUND, annotation.value(),
                "ResponseStatus should have value HttpStatus.NOT_FOUND");
    }
}