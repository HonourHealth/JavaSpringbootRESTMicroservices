package com.example.departmentservice.controller;

import com.example.departmentservice.dto.DepartmentDto;
import com.example.departmentservice.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @InjectMocks
    DepartmentController departmentController;

    @Mock
    DepartmentService departmentService;

    @Test
    void saveDepartment_test() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentCode("depCode");
        departmentDto.setDepartmentDescription("depDesc");
        departmentDto.setId(1L);
        departmentDto.setDepartmentName("depName");
        when(departmentService.saveDepartment(departmentDto)).thenReturn(departmentDto);
        ResponseEntity<DepartmentDto> departmentDtoResponseEntity = departmentController.saveDepartment(departmentDto);
        assertNotNull(departmentDtoResponseEntity);
        assertEquals(departmentDto, departmentDtoResponseEntity.getBody());
        assertEquals(HttpStatus.CREATED, departmentDtoResponseEntity.getStatusCode());
    }

    @Test
    void getDepartment_test() {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentCode("depCode");
        departmentDto.setDepartmentDescription("depDesc");
        departmentDto.setId(1L);
        departmentDto.setDepartmentName("depName");
        when(departmentService.getDepartmentByCode("code")).thenReturn(departmentDto);
        ResponseEntity<DepartmentDto> departmentDtoResponseEntity = departmentController.getDepartment("code");
        assertNotNull(departmentDtoResponseEntity);
        assertEquals(departmentDto, departmentDtoResponseEntity.getBody());
        assertEquals(HttpStatus.OK, departmentDtoResponseEntity.getStatusCode());
    }

}