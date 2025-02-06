package com.example.organizationservice.mapper;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrganizationMapperTest {
    @Test
    void testMapToOrganizationDto() {

        LocalDateTime createdDate = LocalDateTime.now();

        Organization organization = new Organization(
                1L,
                "organizationName",
                "organizationDescription",
                "organizationCode",
                createdDate
        );

        OrganizationDto organizationDto = OrganizationMapper.mapToOrganizationDto(organization);

        assertEquals(organization.getId(), organizationDto.getId());
        assertEquals(organization.getOrganizationName(), organizationDto.getOrganizationName());
        assertEquals(organization.getOrganizationDescription(), organizationDto.getOrganizationDescription());
        assertEquals(organization.getOrganizationCode(), organizationDto.getOrganizationCode());
        assertEquals(organization.getCreatedDate(), organizationDto.getCreatedDate());

        assertNotNull(organizationDto);
    }

    @Test
    void testMapToOrganization() {
        LocalDateTime createdDate = LocalDateTime.now();

        OrganizationDto organizationDto = new OrganizationDto(
                1L,
                "organizationName",
                "organizationDescription",
                "organizationCode",
                createdDate
        );

        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);

        assertEquals(organizationDto.getId(), organization.getId());
        assertEquals(organizationDto.getOrganizationName(), organization.getOrganizationName());
        assertEquals(organizationDto.getOrganizationDescription(), organization.getOrganizationDescription());
        assertEquals(organizationDto.getOrganizationCode(), organization.getOrganizationCode());
        assertEquals(organizationDto.getCreatedDate(), organization.getCreatedDate());

        assertNotNull(organization);
    }
}