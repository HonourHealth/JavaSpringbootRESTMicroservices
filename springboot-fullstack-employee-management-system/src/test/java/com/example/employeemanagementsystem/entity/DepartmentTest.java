package com.example.employeemanagementsystem.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DepartmentTest {
    @Test
    void testGettersAndSetters() {
        Department department = new Department();

        department.setId(1L);
        department.setDepartmentName("departmentName1");
        department.setDepartmentDescription("departmentDescription1");

        assertEquals(1L, department.getId());
        assertEquals("departmentName1", department.getDepartmentName());
        assertEquals("departmentDescription1", department.getDepartmentDescription());
    }

    @Test
    void testNoArgsConstructor() {
        Department department = new Department();
        assertNull(department.getId());
        assertNull(department.getDepartmentName());
        assertNull(department.getDepartmentDescription());
    }

    @Test
    void testAllArgsConstructor() {
        Department department = new Department(
                1L,
                "departmentName1",
                "departmentDescription1"
        );

        assertEquals(1L, department.getId());
        assertEquals("departmentName1", department.getDepartmentName());
        assertEquals("departmentDescription1", department.getDepartmentDescription());
    }
}