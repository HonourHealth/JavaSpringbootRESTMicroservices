package com.example.employeeservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeDtoTest {
    @Test
    void create_employee_dto_with_all_args_constructor() {
        EmployeeDto employeeDto = new EmployeeDto(
                1L,
                "firstName",
                "lastName",
                "email",
                "departmentCode",
                "organizationCode"
        );

        assertEquals(1L, employeeDto.getId());
        assertEquals("firstName", employeeDto.getFirstName());
        assertEquals("lastName", employeeDto.getLastName());
        assertEquals("email", employeeDto.getEmail());
        assertEquals("departmentCode", employeeDto.getDepartmentCode());
        assertEquals("organizationCode", employeeDto.getOrganizationCode());

        assertNotNull(employeeDto);
    }

    @Test
    void create_employee_dto_with_setter() {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.setId(1L);
        employeeDto.setFirstName("firstName");
        employeeDto.setLastName("lastName");
        employeeDto.setEmail("email");
        employeeDto.setDepartmentCode("departmentCode");
        employeeDto.setOrganizationCode("organizationCode");

        assertEquals(1L, employeeDto.getId());
        assertEquals("firstName", employeeDto.getFirstName());
        assertEquals("lastName", employeeDto.getLastName());
        assertEquals("email", employeeDto.getEmail());
        assertEquals("departmentCode", employeeDto.getDepartmentCode());
        assertEquals("organizationCode", employeeDto.getOrganizationCode());

        assertNotNull(employeeDto);
    }

}