package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dto.EmployeeDto;
import com.example.employeemanagementsystem.entity.Department;
import com.example.employeemanagementsystem.entity.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeMapperTest {

    @Test
    void mapToEmployeeDtoTest() {
        Employee employee = new Employee(
                1L,
                "firstName",
                "lastName",
                "email@email.com",
                new Department(1L, "departmentName", "departmentDescription")
        );

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getEmail(), employeeDto.getEmail());
        assertEquals(1L, employee.getDepartment().getId());
        assertEquals("departmentName", employee.getDepartment().getDepartmentName());
        assertEquals("departmentDescription", employee.getDepartment().getDepartmentDescription());
    }

    @Test
    void mapToEmployeeTest() {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(1L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email@email.com");
        employeeDto.setDepartmentId(1L);

        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getEmail(), employeeDto.getEmail());
    }

}