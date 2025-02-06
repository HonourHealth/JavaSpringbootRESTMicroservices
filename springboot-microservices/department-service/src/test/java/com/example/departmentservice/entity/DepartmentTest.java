package com.example.departmentservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DepartmentTest {

    @Test
    void create_department_entity_with_all_arg_constructor() {
        Department department = new Department(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );

        assertEquals(1L, department.getId());
        assertEquals("departmentName", department.getDepartmentName());
        assertEquals("departmentDescription", department.getDepartmentDescription());
        assertEquals("departmentCode", department.getDepartmentCode());

        assertNotNull(department);
    }

    @Test
    void create_department_entity_with_setter() {
        Department department = new Department();

        department.setId(1L);
        department.setDepartmentName("departmentName");
        department.setDepartmentDescription("departmentDescription");
        department.setDepartmentCode("departmentCode");

        assertEquals(1L, department.getId());
        assertEquals("departmentName", department.getDepartmentName());
        assertEquals("departmentDescription", department.getDepartmentDescription());
        assertEquals("departmentCode", department.getDepartmentCode());

        assertNotNull(department);
    }
}