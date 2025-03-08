package com.example.employeemanagementsystem.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmployeeTest {

    @Test
    void testGettersAndSetters() {
        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email@email.com");

        assertEquals(1L, employee.getId());
        assertEquals("firstName", employee.getFirstName());
        assertEquals("lastName", employee.getLastName());
        assertEquals("email@email.com", employee.getEmail());

    }

    @Test
    void testNoArgsConstructor() {
        Employee employee = new Employee();

        assertNull(employee.getId());
        assertNull(employee.getFirstName());
        assertNull(employee.getLastName());
        assertNull(employee.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        Employee employee = new Employee(
                1L,
                "firstName",
                "lastName",
                "email@email.com"
        );

        assertEquals(1L, employee.getId());
        assertEquals("firstName", employee.getFirstName());
        assertEquals("lastName", employee.getLastName());
        assertEquals("email@email.com", employee.getEmail());

    }
}