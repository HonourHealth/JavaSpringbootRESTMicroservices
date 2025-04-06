package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dto.DepartmentDto;
import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @InjectMocks
    DepartmentServiceImpl departmentServiceImpl;

    @Mock
    DepartmentRepository departmentRepository;

    @Test
    void createDepartmentTest() {
        Department department = new Department(
                1L,
                "departmentName1",
                "departmentDescription1"
        );
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        DepartmentDto response = departmentServiceImpl.createDepartment(new DepartmentDto());
        assertNotNull(response);
    }

    @Test
    void getDepartmentByIdTest() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        DepartmentDto departmentById = departmentServiceImpl.getDepartmentById(1L);
        assertNotNull(departmentById);
    }

    @Test
    void getAllDepartmentsTest() {

        List<Department> departmentEntities = List.of(
                new Department(1L, "dept1", "desc1"),
                new Department(2L, "dept2", "desc2")
        );

        when(departmentRepository.findAll()).thenReturn(departmentEntities);

        List<DepartmentDto> response = departmentServiceImpl.getAllDepartments();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(2L, response.get(1).getId());
        assertEquals("dept1", response.get(0).getDepartmentName());
        assertEquals("desc1", response.get(0).getDepartmentDescription());
        assertEquals("dept2", response.get(1).getDepartmentName());
        assertEquals("desc2", response.get(1).getDepartmentDescription());

    }

    @Test
    void updateDepartment_withExistingId() {
        Long departmentId = 1L;
        DepartmentDto updatedDepartmentDto = new DepartmentDto();
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        when(departmentRepository.save(any(Department.class))).thenReturn(new Department());
        DepartmentDto response = departmentServiceImpl.updateDepartment(departmentId, updatedDepartmentDto);
        assertNotNull(response);
    }

    @Test
    void updateDepartment_withNonExistingId_throwsResourceNotFoundException() {
        Long nonExistingId = 999L;
        DepartmentDto updatedDepartmentDto = new DepartmentDto();
        assertThrows(ResourceNotFoundException.class, () -> departmentServiceImpl.updateDepartment(nonExistingId, updatedDepartmentDto));
    }

    @Test
    void deleteDepartment_withExistingId_deletesDepartment() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(new Department()));
        doNothing().when(departmentRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> departmentServiceImpl.deleteDepartment(1L));
    }

    @Test
    void deleteDepartment_withNonExistingId_throwsResourceNotFoundException() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> departmentServiceImpl.deleteDepartment(1L));
    }

}