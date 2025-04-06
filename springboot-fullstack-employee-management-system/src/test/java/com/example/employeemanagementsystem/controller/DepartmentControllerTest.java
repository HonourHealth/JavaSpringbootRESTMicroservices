package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.DepartmentDto;
import com.example.employeemanagementsystem.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @InjectMocks
    DepartmentController departmentController;

    @Mock
    DepartmentService departmentService;

    @Test
    void createDepartmentTest() {
        DepartmentDto departmentDto = new DepartmentDto();
        when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(departmentDto);
        ResponseEntity<DepartmentDto> department = departmentController.createDepartment(new DepartmentDto());
        assertNotNull(department);
        assertEquals(departmentDto, department.getBody());
        assertEquals(HttpStatus.CREATED, department.getStatusCode());
    }

    @Test
    void getDepartmentByIdTest() {
        Long departmentId = 1L;
        when(departmentService.getDepartmentById(anyLong())).thenReturn(new DepartmentDto());
        assertNotNull(departmentController.getDepartmentById(departmentId));
    }

    @Test
    void getAllDepartmentsTest() {
        when(departmentService.getAllDepartments()).thenReturn(new ArrayList<>());
        ResponseEntity<List<DepartmentDto>> response = departmentController.getAllDepartments();
        assertNotNull(response);
    }

    @Test
    void updateDepartmentTest() {
        DepartmentDto updatedDepartmentDto = new DepartmentDto();
        ResponseEntity<DepartmentDto> departmentDtoResponseEntity = departmentController.updateDepartment(1L, updatedDepartmentDto);
        assertNotNull(departmentDtoResponseEntity);
    }

    @Test
    void deleteDepartmentNotFoundTest() {
        doThrow(new RuntimeException("Department not found")).when(departmentService).deleteDepartment(1L);
        assertThrows(RuntimeException.class, () -> departmentController.deleteDepartment(1L));
        verify(departmentService, times(1)).deleteDepartment(1L);
    }

    @Test
    void deleteDepartmentSuccessTest() {
        long departmentId = 1L;

        doNothing().when(departmentService).deleteDepartment(departmentId);

        ResponseEntity<String> response = departmentController.deleteDepartment(departmentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department deleted successfully", response.getBody());

        verify(departmentService, times(1)).deleteDepartment(departmentId);
    }
}