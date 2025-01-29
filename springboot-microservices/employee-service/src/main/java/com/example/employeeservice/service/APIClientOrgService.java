package com.example.employeeservice.service;

import com.example.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface APIClientOrgService {
    @GetMapping("api/organizations/{organization_code}")
    OrganizationDto getOrganization(@PathVariable("organization_code") String organizationCode);
}
