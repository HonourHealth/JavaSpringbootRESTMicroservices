package com.example.organizationservice.service.impl;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {

    @InjectMocks
    OrganizationServiceImpl organizationService;

    @Mock
    OrganizationRepository organizationRepository;

    @Test
    void saveOrganization_test() {
        when(organizationRepository.save(any(Organization.class))).thenReturn(new Organization());
        OrganizationDto response = organizationService.saveOrganization(new OrganizationDto());
        assertNotNull(response);
    }

    @Test
    void getOrganizationByCode() {
        when(organizationRepository.findByOrganizationCode(anyString())).thenReturn(new Organization());
        assertNotNull(organizationService.getOrganizationByCode("organizationCode"));
    }
}