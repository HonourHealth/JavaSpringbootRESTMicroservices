package com.example.employeemanagementsystem.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDtoTest {

    @Test
    void testWithGettersAndSetters() {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(1L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email@email.com");

        assertEquals(1L, employeeDto.getId());
        assertEquals("firstName", employeeDto.getFirstName());
        assertEquals("lastName", employeeDto.getLastName());
        assertEquals("email@email.com", employeeDto.getEmail());

    }

    @Test
    void testWithConstructor() {
        EmployeeDto employeeDto = new EmployeeDto(
                1L,
                "firstName",
                "lastName",
                "email@email.com"
        );

        assertEquals(1L, employeeDto.getId());
        assertEquals("firstName", employeeDto.getFirstName());
        assertEquals("lastName", employeeDto.getLastName());
        assertEquals("email@email.com", employeeDto.getEmail());

    }
}