package com.example.departmentservice.mapper;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentMapperTest {

    @Test
    void testMapToDepartmentDto() {
        Department department = new Department(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );

        DepartmentDto mapToDepartmentDto = DepartmentMapper.mapToDepartmentDto(department);

        assertEquals(department.getId(), mapToDepartmentDto.getId());
        assertEquals(department.getDepartmentName(), mapToDepartmentDto.getDepartmentName());
        assertEquals(department.getDepartmentDescription(), mapToDepartmentDto.getDepartmentDescription());
        assertEquals(department.getDepartmentCode(), mapToDepartmentDto.getDepartmentCode());
    }

    @Test
    void testMapToDepartment() {
        DepartmentDto departmentDto = new DepartmentDto(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );

        Department mapToDepartment = DepartmentMapper.mapToDepartment(departmentDto);

        assertEquals(departmentDto.getId(), mapToDepartment.getId());
        assertEquals(departmentDto.getDepartmentName(), mapToDepartment.getDepartmentName());
        assertEquals(departmentDto.getDepartmentDescription(), mapToDepartment.getDepartmentDescription());
        assertEquals(departmentDto.getDepartmentCode(), mapToDepartment.getDepartmentCode());
    }
}