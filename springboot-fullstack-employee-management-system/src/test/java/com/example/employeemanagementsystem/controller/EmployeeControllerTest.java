package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.EmployeeDto;
import com.example.employeemanagementsystem.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Test
    void createEmployeeTest() {
        EmployeeDto employeeDto = new EmployeeDto();
        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(employeeDto);
        ResponseEntity<EmployeeDto> employee = employeeController.createEmployee(new EmployeeDto());
        assertNotNull(employee);
        assertEquals(employeeDto, employee.getBody());
        assertEquals(HttpStatus.CREATED, employee.getStatusCode());
    }

    @Test
    void getEmployeeByIdTest() {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(new EmployeeDto());
        assertNotNull(employeeController.getEmployeeById(1L));
    }

    @Test
    void getAllEmployeesTest() {
        when(employeeService.getAllEmployees()).thenReturn(new ArrayList<>());
        assertNotNull(employeeController.getAllEmployees());
    }

    @Test
    void updateEmployeeTest() {
        when(employeeService.updateEmployee(anyLong(), any(EmployeeDto.class))).thenReturn(new EmployeeDto());
        assertNotNull(employeeController.updateEmployee(1L, new EmployeeDto()));
    }


    @Test
    public void testDeleteEmployee_Success() {
        Long employeeId = 1L;

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);

        verify(employeeService, times(1)).deleteEmployee(employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee deleted successfully!", response.getBody());
    }

    @Test
    public void testDeleteEmployee_ServiceThrowsException() {
        Long employeeId = 1L;
        doThrow(new RuntimeException("Employee not found")).when(employeeService).deleteEmployee(employeeId);

        try {
            employeeController.deleteEmployee(employeeId);
        } catch (RuntimeException ex) {
            assertEquals("Employee not found", ex.getMessage());
            verify(employeeService, times(1)).deleteEmployee(employeeId);
        }
    }

}