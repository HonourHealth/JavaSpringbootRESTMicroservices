package com.example.employeeservice.mapper;

import com.example.employeeservice.dto.EmployeeDto;
import com.example.employeeservice.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    @Test
    void testMapToEmployeeDto () {
        Employee employee = new Employee(
                1L,
                "firstName",
                "lastName",
                "email",
                "departmentCode",
                "organizationCode"
        );

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        assertEquals(employee.getId(),employeeDto.getId());
        assertEquals(employee.getFirstName(),employeeDto.getFirstName());
        assertEquals(employee.getLastName(),employeeDto.getLastName());
        assertEquals(employee.getEmail(),employeeDto.getEmail());
        assertEquals(employee.getDepartmentCode(),employeeDto.getDepartmentCode());
        assertEquals(employee.getOrganizationCode(),employeeDto.getOrganizationCode());

        assertNotNull(employeeDto);
    }

    @Test
    void testMapToEmployee () {
        EmployeeDto employeeDto = new EmployeeDto(
                1L,
                "firstName",
                "lastName",
                "email",
                "departmentCode",
                "organizationCode"
        );

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        assertEquals(employeeDto.getId(),employee.getId());
        assertEquals(employeeDto.getFirstName(),employee.getFirstName());
        assertEquals(employeeDto.getLastName(),employee.getLastName());
        assertEquals(employeeDto.getEmail(),employee.getEmail());
        assertEquals(employeeDto.getDepartmentCode(),employee.getDepartmentCode());
        assertEquals(employeeDto.getOrganizationCode(),employee.getOrganizationCode());

        assertNotNull(employee);
    }

}