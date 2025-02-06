package com.example.departmentservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DepartmentDtoTest {

    @Test
    void create_department_dto_with_all_arg_constructor() {
        DepartmentDto departmentDto = new DepartmentDto(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );

        assertEquals(1L, departmentDto.getId());
        assertEquals("departmentName", departmentDto.getDepartmentName());
        assertEquals("departmentDescription", departmentDto.getDepartmentDescription());
        assertEquals("departmentCode", departmentDto.getDepartmentCode());

        assertNotNull(departmentDto);
    }

    @Test
    void create_department_dto_with_setter() {
        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setId(1L);
        departmentDto.setDepartmentName("departmentName");
        departmentDto.setDepartmentDescription("departmentDescription");
        departmentDto.setDepartmentCode("departmentCode");

        assertEquals(1L, departmentDto.getId());
        assertEquals("departmentName", departmentDto.getDepartmentName());
        assertEquals("departmentDescription", departmentDto.getDepartmentDescription());
        assertEquals("departmentCode", departmentDto.getDepartmentCode());

        assertNotNull(departmentDto);
    }
}