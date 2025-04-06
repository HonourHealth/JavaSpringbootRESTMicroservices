package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dto.DepartmentDto;
import com.example.employeemanagementsystem.entity.Department;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentMapperTest {

    @Test
    void mapToDepartmentDtoTest() {
        Department department = new Department(
                1L,
                "departmentName1",
                "departmentDescription1"
        );

        DepartmentDto departmentDto = DepartmentMapper.mapToDepartmentDto(department);

        assertEquals(department.getId(), departmentDto.getId());
        assertEquals(department.getDepartmentName(), departmentDto.getDepartmentName());
        assertEquals(department.getDepartmentDescription(), departmentDto.getDepartmentDescription());
    }

    @Test
    void mapToDepartmentTest() {
        DepartmentDto departmentDto = new DepartmentDto(
                1L,
                "departmentName1",
                "departmentDescription1"
        );

        Department department = DepartmentMapper.mapToDepartment(departmentDto);

        assertEquals(department.getId(), departmentDto.getId());
        assertEquals(department.getDepartmentName(), departmentDto.getDepartmentName());
        assertEquals(department.getDepartmentDescription(), departmentDto.getDepartmentDescription());
    }
}