package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dto.EmployeeDto;
import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    EmployeeServiceImpl employeeServiceImpl;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void createEmployeeTest() {
        Employee employee = new Employee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto response = employeeServiceImpl.createEmployee(new EmployeeDto());
        assertNotNull(response);
    }

    @Test
    void getEmployeeByIdTest() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        assertNotNull(employeeServiceImpl.getEmployeeById(1L));
    }

    @Test
    void getEmployeeById_withExistingId_returnsCorrectEmployee() {
        Long existingId = 100L;
        Employee employee = new Employee(existingId, "firstName", "lastName", "email@email.com");

        when(employeeRepository.findById(existingId)).thenReturn(Optional.of(employee));

        EmployeeDto result = employeeServiceImpl.getEmployeeById(existingId);

        assertNotNull(result);
        assertEquals(existingId, result.getId());
        assertEquals("firstName", result.getFirstName());
        assertEquals("lastName", result.getLastName());
        assertEquals("email@email.com", result.getEmail());
    }

    @Test
    void getEmployeeById_withNonExistingId_throwsResourceNotFoundException() {

        Long nonExistingId = 999L;
        when(employeeRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.getEmployeeById(nonExistingId));
    }

    @Test
    void getAllEmployeesTest() {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        assertNotNull(employeeServiceImpl.getAllEmployees());
    }

    @Test
    void getAllEmployees_withMultipleEmployees_returnsMatchingList() {
        Employee e1 = new Employee(1L, "Alice", "Wonderland", "alice@example.com");
        Employee e2 = new Employee(2L, "Bob", "Marley", "bob@example.com");
        List<Employee> employees = new ArrayList<>();
        employees.add(e1);
        employees.add(e2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDto> result = employeeServiceImpl.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());

        EmployeeDto dto1 = result.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Alice", dto1.getFirstName());
        assertEquals("Wonderland", dto1.getLastName());
        assertEquals("alice@example.com", dto1.getEmail());

        EmployeeDto dto2 = result.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Bob", dto2.getFirstName());
        assertEquals("Marley", dto2.getLastName());
        assertEquals("bob@example.com", dto2.getEmail());
    }

    @Test
    void updateEmployeeTest() {
        Long employeeId = 1L;

        EmployeeDto updatedEmployeeDto = new EmployeeDto(
                employeeId,
                "updatedFirstName",
                "updatedLastName",
                "updated@email.com"
        );

        Employee existingEmployee = new Employee(
                employeeId,
                "firstName",
                "lastName",
                "email@email.com"
        );

        Employee updatedEmployee = new Employee(
                employeeId,
                "updatedFirstName",
                "updatedLastName",
                "updated@email.com"
        );

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        EmployeeDto response = employeeServiceImpl.updateEmployee(employeeId, updatedEmployeeDto);
        assertNotNull(response);
        assertEquals(employeeId, response.getId());
        assertEquals("updatedFirstName", response.getFirstName());
        assertEquals("updatedLastName", response.getLastName());
        assertEquals("updated@email.com", response.getEmail());

    }

    @Test
    void updateEmployee_withNonExistingId_throwsResourceNotFoundException() {
        Long nonExistingId = 999L;
        EmployeeDto updatedEmployeeDto = new EmployeeDto(nonExistingId, "NewFirst", "NewLast", "new@example.com");

        when(employeeRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.updateEmployee(nonExistingId, updatedEmployeeDto));
    }

    @Test
    void deleteEmployee_withExistingId_deletesUser() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(new Employee()));
        doNothing().when(employeeRepository).deleteById(anyLong());

        assertDoesNotThrow(() -> employeeServiceImpl.deleteEmployee(1L));
    }

    @Test
    void deleteEmployee_withNonExistingId_throwsResourceNotFoundException() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeServiceImpl.deleteEmployee(1L));
    }

}