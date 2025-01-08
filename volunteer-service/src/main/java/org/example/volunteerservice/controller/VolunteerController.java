package org.example.volunteerservice.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.constraints.Min;
import org.example.volunteerservice.dto.*;
import org.example.volunteerservice.exceptions.InvalidDataException;
import org.example.volunteerservice.exceptions.ResourceNotFoundException;
import org.example.volunteerservice.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDto> getVolunteerById(@PathVariable Integer id) {
        try {
            VolunteerDto volunteer = volunteerService.getVolunteerById(id);
            return ResponseEntity.ok(volunteer);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<VolunteerDto>> getAllVolunteers() {
        List<VolunteerDto> volunteers = volunteerService.getAllVolunteers();
        if (volunteers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(volunteers);
    }

    @GetMapping("/skills/{volunteerId}")
    public ResponseEntity<List<SkillDto>> getSkillsForVolunteer(@PathVariable Integer volunteerId) {
        try {
            List<SkillDto> skills = volunteerService.getSkillsForVolunteer(volunteerId);
            if (skills.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(skills);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<VolunteerDto> createVolunteer(@Validated @RequestBody VolunteerRegisterDto volunteerDto) {
        try {
            VolunteerDto createdVolunteer = volunteerService.createVolunteer(volunteerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVolunteer);
        } catch (InvalidDataException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/update/{id}/username")
    public ResponseEntity<VolunteerDto> updateUsername(@PathVariable Integer id, @RequestBody UpdateUsernameDto updateUsernameDto) {
        VolunteerDto updatedVolunteer = volunteerService.updateUsername(id, updateUsernameDto);
        return ResponseEntity.ok(updatedVolunteer);
    }

    @PutMapping("/update/{id}/password")
    public ResponseEntity<VolunteerDto> updatePassword(@PathVariable Integer id, @RequestBody UpdatePasswordDto updatePasswordDto) {
        VolunteerDto updatedVolunteer = volunteerService.updatePassword(id, updatePasswordDto);
        return ResponseEntity.ok(updatedVolunteer);
    }

    @PostMapping("/login")
    public ResponseEntity<VolunteerDto> loginVolunteer(@RequestBody LoginDto loginDto) {
        VolunteerDto volunteerDto = volunteerService.loginVolunteer(loginDto);
        return ResponseEntity.ok(volunteerDto);
    }

    @GetMapping("/filter")
    @RateLimiter(name="filterVolunteers", fallbackMethod = "fallbackFilterVolunteersByNumberOfSkills")
    public ResponseEntity<List<VolunteerDto>> filterVolunteersByNumberOfSkills(@RequestParam @Min(1) Integer min){
        return new ResponseEntity<>(volunteerService.filterVolunteersByNumberOfSkills(min), HttpStatus.OK);
    }

    public ResponseEntity<List<VolunteerDto>> fallbackFilterVolunteersByNumberOfSkills(Exception exception) {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.TOO_MANY_REQUESTS);
    }

}
