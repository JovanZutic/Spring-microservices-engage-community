package org.example.volunteerservice.service;

import org.example.volunteerservice.dto.*;
import org.example.volunteerservice.exceptions.InvalidPasswordException;
import org.example.volunteerservice.exceptions.VolunteerNotFoundException;
import org.example.volunteerservice.model.Skill;
import org.example.volunteerservice.model.Volunteer;
import org.example.volunteerservice.repos.VolunteerRepos;
import org.example.volunteerservice.repos.VolunteerSkillRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepos volunteerRepos;
    @Autowired
    private VolunteerSkillRepos volunteerSkillRepos;

    public VolunteerDto getVolunteerById(Integer id) {
        Volunteer volunteer = volunteerRepos.findById(id).get();
        VolunteerDto volunteerDto = new VolunteerDto();
        volunteerDto.setId(volunteer.getId());
        volunteerDto.setName(volunteer.getName());
        volunteerDto.setEmail(volunteer.getEmail());
        volunteerDto.setBio(volunteer.getBio());
        return volunteerDto;
    }

    public List<VolunteerDto> getAllVolunteers(){
        List<VolunteerDto> volunteerDtos = new ArrayList<>();
        List<Volunteer> volunteers = volunteerRepos.findAll();
        for(Volunteer volunteer : volunteers){
            VolunteerDto volunteerDto = new VolunteerDto();
            volunteerDto.setId(volunteer.getId());
            volunteerDto.setName(volunteer.getName());
            volunteerDto.setEmail(volunteer.getEmail());
            volunteerDto.setBio(volunteer.getBio());
            volunteerDtos.add(volunteerDto);
        }
        return volunteerDtos;
    }

    public List<SkillDto> getSkillsForVolunteer(Integer volunteerId){
        List<SkillDto> skillDtos = new ArrayList<>();
        List<Skill> skills = volunteerSkillRepos.findSkillsByVolunteerId(volunteerId);
        for(Skill skill : skills){
            SkillDto skillDto = new SkillDto();
            skillDto.setId(skill.getId());
            skillDto.setName(skill.getName());
            skillDtos.add(skillDto);
        }
        return skillDtos;
    }

    public VolunteerDto createVolunteer(VolunteerRegisterDto volunteerDto) {
        Volunteer volunteer = new Volunteer();
        volunteer.setName(volunteerDto.getName());
        volunteer.setEmail(volunteerDto.getEmail());
        volunteer.setBio(volunteerDto.getBio());
        volunteer.setCreatedAt(java.time.Instant.now());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(volunteerDto.getPassword());
        volunteer.setPassword(hashedPassword);

        Volunteer savedVolunteer = volunteerRepos.save(volunteer);

        return mapToDto(savedVolunteer);
    }

    public VolunteerDto updateUsername(Integer id, UpdateUsernameDto updateUsernameDto) {
        Optional<Volunteer> optionalVolunteer = volunteerRepos.findById(id);

        if (!optionalVolunteer.isPresent()) {
            throw new VolunteerNotFoundException("Volunteer with ID " + id + " not found");
        }

        Volunteer volunteer = optionalVolunteer.get();
        volunteer.setName(updateUsernameDto.getName());
        Volunteer updatedVolunteer = volunteerRepos.save(volunteer);
        return mapToDto(updatedVolunteer);
    }

    public VolunteerDto updatePassword(Integer id, UpdatePasswordDto updatePasswordDto) {
        Optional<Volunteer> optionalVolunteer = volunteerRepos.findById(id);

        if (!optionalVolunteer.isPresent()) {
            throw new VolunteerNotFoundException("Volunteer with ID " + id + " not found");
        }

        Volunteer volunteer = optionalVolunteer.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(updatePasswordDto.getPassword());

        volunteer.setPassword(hashedPassword);
        Volunteer updatedVolunteer = volunteerRepos.save(volunteer);
        return mapToDto(updatedVolunteer);
    }

    public VolunteerDto loginVolunteer(LoginDto loginDto) {
        Optional<Volunteer> optionalVolunteer = volunteerRepos.findByEmail(loginDto.getEmail());

        if (!optionalVolunteer.isPresent()) {
            throw new VolunteerNotFoundException("No volunteer found with the provided email.");
        }

        Volunteer volunteer = optionalVolunteer.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDto.getPassword(), volunteer.getPassword())) {
            throw new InvalidPasswordException("Invalid password.");
        }

        return mapToDto(volunteer);
    }

    public List<VolunteerDto> filterVolunteersByNumberOfSkills(Integer min){
        List<Volunteer> allVolunteers = volunteerRepos.findAll();
        List<VolunteerDto> volunteerDtos = new ArrayList<>();
        for (Volunteer v : allVolunteers) {
            if(volunteerSkillRepos.findSkillsByVolunteerId(v.getId()).size() >= min){
                volunteerDtos.add(mapToDto(v));
            }
        }

        return volunteerDtos;
    }

    private VolunteerDto mapToDto(Volunteer volunteer) {
        VolunteerDto dto = new VolunteerDto();
        dto.setId(volunteer.getId());
        dto.setName(volunteer.getName());
        dto.setEmail(volunteer.getEmail());
        dto.setBio(volunteer.getBio());
        return dto;
    }

}
