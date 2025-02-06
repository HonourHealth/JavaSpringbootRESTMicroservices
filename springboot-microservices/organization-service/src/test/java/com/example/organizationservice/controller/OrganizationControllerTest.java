package com.example.organizationservice.controller;

import com.example.organizationservice.dto.OrganizationDto;
import com.example.organizationservice.service.OrganizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationControllerTest {

    @InjectMocks
    OrganizationController organizationController;

    @Mock
    OrganizationService organizationService;

    @Test
    void saveOrganization_test() {
        when(organizationService.saveOrganization(any(OrganizationDto.class))).thenReturn(new OrganizationDto());
        ResponseEntity<OrganizationDto> organizationDtoResponseEntity = organizationController.saveOrganization(new OrganizationDto());
        assertNotNull(organizationDtoResponseEntity);
    }

    @Test
    void getOrganization_test() {
        when(organizationService.getOrganizationByCode(anyString())).thenReturn(new OrganizationDto());
        ResponseEntity<OrganizationDto> response = organizationController.getOrganization("organizationCode");
        assertNotNull(response);
    }

}