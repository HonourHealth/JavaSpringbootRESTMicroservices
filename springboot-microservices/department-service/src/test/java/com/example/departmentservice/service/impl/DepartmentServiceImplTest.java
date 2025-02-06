package com.example.departmentservice.service.impl;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Mock
    DepartmentRepository departmentRepository;

    @Test
    void saveDepartment_test() {
        DepartmentDto departmentDto = new DepartmentDto(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );

        Department department = new Department(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );

        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDto response = departmentService.saveDepartment(departmentDto);
        assertNotNull(response);
    }

    @Test
    void getDepartmentByCode_test() {
        Department department = new Department(
                1L,
                "departmentName",
                "departmentDescription",
                "departmentCode"
        );
        when(departmentRepository.findByDepartmentCode(anyString())).thenReturn(department);
        DepartmentDto response = departmentService.getDepartmentByCode("departmentCode");
        assertNotNull(response);
    }
}