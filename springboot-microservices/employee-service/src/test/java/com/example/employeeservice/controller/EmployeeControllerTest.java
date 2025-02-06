package com.example.employeeservice.controller;

import com.example.employeeservice.dto.APIResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.dto.OrganizationDto;
import com.example.employeeservice.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Test
    void saveEmployee_test() {
        when(employeeService.saveEmployee(any(EmployeeDto.class))).thenReturn(new EmployeeDto());
        ResponseEntity<EmployeeDto> response = employeeController.saveEmployee(new EmployeeDto());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getEmployee_test() {
        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setOrganizationDto(new OrganizationDto());
        apiResponseDto.setEmployeeDto(new EmployeeDto());
        apiResponseDto.setDepartmentDto(new DepartmentDto());
        when(employeeService.getEmployeeById(anyLong())).thenReturn(apiResponseDto);
        ResponseEntity<APIResponseDto> response = employeeController.getEmployee(1L);
        assertNotNull(response);
        assertEquals(apiResponseDto, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}