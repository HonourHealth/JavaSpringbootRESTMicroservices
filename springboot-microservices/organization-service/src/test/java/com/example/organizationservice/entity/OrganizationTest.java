package com.example.organizationservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrganizationTest {

    @Test
    void create_organization_entity_with_all_args_constructor() {

        LocalDateTime createdDate = LocalDateTime.now();

        Organization organization = new Organization(
                1L,
                "organizationName",
                "organizationDescription",
                "organizationCode",
                createdDate
        );

        assertEquals(1L, organization.getId());
        assertEquals("organizationName", organization.getOrganizationName());
        assertEquals("organizationDescription", organization.getOrganizationDescription());
        assertEquals("organizationCode", organization.getOrganizationCode());
        assertEquals(createdDate, organization.getCreatedDate());

        assertNotNull(organization);
    }

    @Test
    void create_organization_entity_with_setter() {
        LocalDateTime createdDate = LocalDateTime.now();

        Organization organization = new Organization();

        organization.setId(1L);
        organization.setOrganizationName("organizationName");
        organization.setOrganizationDescription("organizationDescription");
        organization.setOrganizationCode("organizationCode");
        organization.setCreatedDate(createdDate);

        assertEquals(1L, organization.getId());
        assertEquals("organizationName", organization.getOrganizationName());
        assertEquals("organizationDescription", organization.getOrganizationDescription());
        assertEquals("organizationCode", organization.getOrganizationCode());
        assertEquals(createdDate, organization.getCreatedDate());

        assertNotNull(organization);
    }
}