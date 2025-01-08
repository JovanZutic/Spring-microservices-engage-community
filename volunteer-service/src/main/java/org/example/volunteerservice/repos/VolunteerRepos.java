package org.example.volunteerservice.repos;

import org.example.volunteerservice.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepos extends JpaRepository<Volunteer, Integer> {

    Optional<Volunteer> findByEmail(String email);

}
