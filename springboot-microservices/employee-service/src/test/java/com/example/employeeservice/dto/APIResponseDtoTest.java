package com.example.employeeservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class APIResponseDtoTest {

    @Test
    void create_api_response_dto_with_all_args_constructor() {
        EmployeeDto employeeDto = new EmployeeDto();
        DepartmentDto departmentDto = new DepartmentDto();
        OrganizationDto organizationDto = new OrganizationDto();
        APIResponseDto apiResponseDto = new APIResponseDto(
                employeeDto,
                departmentDto,
                organizationDto
        );
        assertEquals(employeeDto, apiResponseDto.getEmployeeDto());
        assertEquals(departmentDto, apiResponseDto.getDepartmentDto());
        assertEquals(organizationDto, apiResponseDto.getOrganizationDto());
        assertNotNull(apiResponseDto);
    }

    @Test
    void create_api_response_dto_with_setter() {
        EmployeeDto employeeDto = new EmployeeDto();
        DepartmentDto departmentDto = new DepartmentDto();
        OrganizationDto organizationDto = new OrganizationDto();

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);

        assertEquals(employeeDto, apiResponseDto.getEmployeeDto());
        assertEquals(departmentDto, apiResponseDto.getDepartmentDto());
        assertEquals(organizationDto, apiResponseDto.getOrganizationDto());
        assertNotNull(apiResponseDto);
    }

}