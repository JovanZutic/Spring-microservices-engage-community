package org.example.organizationservice.service;

import org.example.organizationservice.dto.*;
import org.example.organizationservice.exceptions.InvalidPasswordException;
import org.example.organizationservice.exceptions.OrganizationNotFoundException;
import org.example.organizationservice.feign.EventProxy;
import org.example.organizationservice.model.Organization;
import org.example.organizationservice.repos.OrganizationRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepos organizationRepos;
    @Autowired
    private EventProxy eventProxy;

    public List<OrganizationDto> getAllOrganizations() {
        List<Organization> organizations = organizationRepos.findAll();
        List<OrganizationDto> organizationDtos = new ArrayList<>();
        for (Organization organization : organizations) {
            OrganizationDto organizationDto = new OrganizationDto();
            organizationDto.setId(organization.getId());
            organizationDto.setName(organization.getName());
            organizationDto.setEmail(organization.getEmail());
            organizationDto.setDomain(organization.getDomain());
            organizationDto.setCreatedAt(organization.getCreatedAt());
            organizationDtos.add(organizationDto);
        }
        return organizationDtos;
    }

    public OrganizationDto getOrganizationById(Integer id) {
        Organization org = organizationRepos.findById(id).get();
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(org.getId());
        organizationDto.setName(org.getName());
        organizationDto.setEmail(org.getEmail());
        organizationDto.setDomain(org.getDomain());
        organizationDto.setCreatedAt(org.getCreatedAt());
        return organizationDto;
    }

    public OrgCommentsDto getOrgWithComments(Integer id) {
        Organization org = organizationRepos.findById(id)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization with ID " + id + " not found."));

        OrganizationDto organizationDto = new OrganizationDto();
        if(org != null){
            organizationDto.setId(org.getId());
            organizationDto.setName(org.getName());
            organizationDto.setEmail(org.getEmail());
            organizationDto.setDomain(org.getDomain());
            organizationDto.setCreatedAt(org.getCreatedAt());
        }

        List<CommentsDto> comms = eventProxy.getCommentsByOrganizationId(id);
        OrgCommentsDto orcomm = new OrgCommentsDto();
        if(comms != null){
            orcomm.setOrganization(organizationDto);
            orcomm.setComment(comms);
        }
        return orcomm;
    }

    public OrganizationDto createOrganization(OrganizationRegisterDto organizationDto) {
        Organization organization = new Organization();
        organization.setName(organizationDto.getName());
        organization.setEmail(organizationDto.getEmail());
        organization.setDomain(organizationDto.getDomain());
        organization.setCreatedAt(java.time.Instant.now());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(organizationDto.getPassword());
        organization.setPassword(hashedPassword);

        Organization savedOrganization = organizationRepos.save(organization);
        return mapToDto(savedOrganization);
    }

    public OrganizationDto updateUsername(Integer id, UpdateUsernameDto updateUsernameDto) {
        Optional<Organization> optionalOrganization = organizationRepos.findById(id);

        if (!optionalOrganization.isPresent()) {
            throw new OrganizationNotFoundException("Organization with ID " + id + " not found");
        }

        Organization organization = optionalOrganization.get();
        organization.setName(updateUsernameDto.getName());
        Organization updatedOrganization = organizationRepos.save(organization);
        return mapToDto(updatedOrganization);
    }

    public OrganizationDto updatePassword(Integer id, UpdatePasswordDto updatePasswordDto) {
        Optional<Organization> optionalOrganization = organizationRepos.findById(id);

        if (!optionalOrganization.isPresent()) {
            throw new OrganizationNotFoundException("Organization with ID " + id + " not found");
        }

        Organization organization = optionalOrganization.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(updatePasswordDto.getPassword());

        organization.setPassword(hashedPassword);
        Organization updatedOrganization = organizationRepos.save(organization);
        return mapToDto(updatedOrganization);
    }

    public OrganizationDto loginOrganization(LoginDto loginDto) {
        Optional<Organization> optionalOrganization = organizationRepos.findByEmail(loginDto.getEmail());

        if (!optionalOrganization.isPresent()) {
            throw new OrganizationNotFoundException("No organization found with the provided email.");
        }

        Organization organization = optionalOrganization.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDto.getPassword(), organization.getPassword())) {
            throw new InvalidPasswordException("Invalid password.");
        }

        return mapToDto(organization);
    }

    // Pomoćna metoda za mapiranje u DTO
    private OrganizationDto mapToDto(Organization organization) {
        OrganizationDto dto = new OrganizationDto();
        dto.setId(organization.getId());
        dto.setName(organization.getName());
        dto.setEmail(organization.getEmail());
        dto.setDomain(organization.getDomain());
        return dto;
    }


}
