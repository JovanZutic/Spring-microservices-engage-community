package org.example.organizationservice.repos;

import org.example.organizationservice.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepos extends JpaRepository<Organization, Integer> {

    Optional<Organization> findByEmail(String email);

}
