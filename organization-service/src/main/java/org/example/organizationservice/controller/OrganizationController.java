package org.example.organizationservice.controller;

import org.example.organizationservice.dto.*;
import org.example.organizationservice.exceptions.InvalidDataException;
import org.example.organizationservice.exceptions.ResourceNotFoundException;
import org.example.organizationservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/all")
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {
        List<OrganizationDto> organizations = organizationService.getAllOrganizations();
        if (organizations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable Integer id) {
        try {
            OrganizationDto organization = organizationService.getOrganizationById(id);
            return ResponseEntity.ok(organization);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/org-comm/{id}")
    public OrgCommentsDto getOrgWithComments(@PathVariable Integer id){
        return organizationService.getOrgWithComments(id);
    }

    @PostMapping("/c    reate")
    public ResponseEntity<OrganizationDto> createOrganization(@Validated @RequestBody OrganizationRegisterDto organizationDto) {
        try {
            OrganizationDto createdOrganization = organizationService.createOrganization(organizationDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrganization);
        } catch (InvalidDataException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}/username")
    public ResponseEntity<OrganizationDto> updateUsername(@PathVariable Integer id, @RequestBody UpdateUsernameDto updateUsernameDto) {
        OrganizationDto updatedOrganization = organizationService.updateUsername(id, updateUsernameDto);
        return ResponseEntity.ok(updatedOrganization);
    }

    @PutMapping("/update/{id}/password")
    public ResponseEntity<OrganizationDto> updatePassword(@PathVariable Integer id, @RequestBody UpdatePasswordDto updatePasswordDto) {
        OrganizationDto updatedOrganization = organizationService.updatePassword(id, updatePasswordDto);
        return ResponseEntity.ok(updatedOrganization);
    }

    @PostMapping("/login")
    public ResponseEntity<OrganizationDto> loginOrganization(@RequestBody LoginDto loginDto) {
        OrganizationDto organization = organizationService.loginOrganization(loginDto);
        return ResponseEntity.ok(organization);
    }

}
