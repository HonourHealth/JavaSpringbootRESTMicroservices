package com.example.employeeservice.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeTest {
    @Test
    void create_employee_entity_with_all_args_constructor() {
        Employee employee = new Employee(
                1L,
                "firstName",
                "lastName",
                "email",
                "departmentCode",
                "organizationCode"
        );

        assertEquals(1L, employee.getId());
        assertEquals("firstName", employee.getFirstName());
        assertEquals("lastName", employee.getLastName());
        assertEquals("email", employee.getEmail());
        assertEquals("departmentCode", employee.getDepartmentCode());
        assertEquals("organizationCode", employee.getOrganizationCode());

        assertNotNull(employee);
    }

    @Test
    void create_employee_entity_with_setter() {
        Employee employee = new Employee();

        employee.setId(1L);
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setEmail("email");
        employee.setDepartmentCode("departmentCode");
        employee.setOrganizationCode("organizationCode");

        assertEquals(1L, employee.getId());
        assertEquals("firstName", employee.getFirstName());
        assertEquals("lastName", employee.getLastName());
        assertEquals("email", employee.getEmail());
        assertEquals("departmentCode", employee.getDepartmentCode());
        assertEquals("organizationCode", employee.getOrganizationCode());

        assertNotNull(employee);
    }
}