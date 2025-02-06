package com.example.organizationservice.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrganizationDtoTest {

    @Test
    void create_organization_dto_with_all_args_constructor() {

        LocalDateTime createdDate = LocalDateTime.now();

        OrganizationDto organizationDto = new OrganizationDto(
                1L,
                "organizationName",
                "organizationDescription",
                "organizationCode",
                createdDate
        );

        assertEquals(1L, organizationDto.getId());
        assertEquals("organizationName", organizationDto.getOrganizationName());
        assertEquals("organizationDescription", organizationDto.getOrganizationDescription());
        assertEquals("organizationCode", organizationDto.getOrganizationCode());
        assertEquals(createdDate, organizationDto.getCreatedDate());

        assertNotNull(organizationDto);
    }

    @Test
    void create_organization_dto_with_setter() {
        LocalDateTime createdDate = LocalDateTime.now();

        OrganizationDto organizationDto = new OrganizationDto();

        organizationDto.setId(1L);
        organizationDto.setOrganizationName("organizationName");
        organizationDto.setOrganizationDescription("organizationDescription");
        organizationDto.setOrganizationCode("organizationCode");
        organizationDto.setCreatedDate(createdDate);

        assertEquals(1L, organizationDto.getId());
        assertEquals("organizationName", organizationDto.getOrganizationName());
        assertEquals("organizationDescription", organizationDto.getOrganizationDescription());
        assertEquals("organizationCode", organizationDto.getOrganizationCode());
        assertEquals(createdDate, organizationDto.getCreatedDate());

        assertNotNull(organizationDto);
    }
}