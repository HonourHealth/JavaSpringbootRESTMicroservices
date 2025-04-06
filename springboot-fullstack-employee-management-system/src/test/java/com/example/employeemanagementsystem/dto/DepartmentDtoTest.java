package com.example.employeemanagementsystem.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentDtoTest {

    @Test
    void testWithGettersAndSetters() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(1L);
        departmentDto.setDepartmentName("departmentName1");
        departmentDto.setDepartmentDescription("departmentDescription1");

        assertEquals(1L, departmentDto.getId());
        assertEquals("departmentName1", departmentDto.getDepartmentName());
        assertEquals("departmentDescription1", departmentDto.getDepartmentDescription());
    }

    @Test
    void testWithConstructor() {
        DepartmentDto departmentDto = new DepartmentDto(
                1L,
                "departmentName1",
                "departmentDescription1"
        );
        assertEquals(1L, departmentDto.getId());
        assertEquals("departmentName1", departmentDto.getDepartmentName());
        assertEquals("departmentDescription1", departmentDto.getDepartmentDescription());
    }

}