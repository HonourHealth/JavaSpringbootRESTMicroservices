package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.APIResponseDto;
import com.example.employeeservice.dto.DepartmentDto;
import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.dto.OrganizationDto;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.APIClientDepService;
import com.example.employeeservice.service.APIClientOrgService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    APIClientDepService apiClientDepService;

    @Mock
    APIClientOrgService apiClientOrgService;

    @Test
    void saveEmployee_test() {
        EmployeeDto employeeDto = new EmployeeDto();
        Employee employee = new Employee();
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto);
        assertNotNull(savedEmployee);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_test() {
        Employee employee = new Employee(
                1L,
                "firstName",
                "lastName",
                "email",
                "departmentCode",
                "organizationCode"
        );

        Optional<Employee> t = Optional.of(employee);

        when(employeeRepository.findById(anyLong())).thenReturn(t);
        when(apiClientDepService.getDepartment(anyString())).thenReturn(new DepartmentDto());
        when(apiClientOrgService.getOrganization(anyString())).thenReturn(new OrganizationDto());

        APIResponseDto response = employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertNotNull(response.getEmployeeDto());
        assertNotNull(response.getDepartmentDto());
        assertNotNull(response.getOrganizationDto());
        verify(employeeRepository).findById(anyLong());
        verify(apiClientDepService).getDepartment(anyString());
        verify(apiClientOrgService).getOrganization(anyString());
    }

    @Test
    void getDefaultDepartment_test() {
        Exception exception = new Exception();

        Employee employee = new Employee(
                1L,
                "firstName",
                "lastName",
                "email",
                "departmentCode",
                "organizationCode"
        );

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        APIResponseDto response = employeeService.getDefaultDepartment(1L, exception);

        assertNotNull(response);
        assertNotNull(response.getEmployeeDto());
        assertNotNull(response.getDepartmentDto());
        verify(employeeRepository).findById(anyLong());
    }

    @Test
    void getEmployeeById_notFound_test() {
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> employeeService.getEmployeeById(1L));
        verify(employeeRepository).findById(anyLong());
    }
}