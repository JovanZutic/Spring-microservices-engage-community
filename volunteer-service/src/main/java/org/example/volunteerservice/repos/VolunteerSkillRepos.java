package org.example.volunteerservice.repos;

import org.example.volunteerservice.model.Skill;
import org.example.volunteerservice.model.VolunteerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolunteerSkillRepos extends JpaRepository<VolunteerSkill, Integer> {

    @Query("SELECT s FROM Skill s JOIN VolunteerSkill vs ON s.id = vs.skill.id WHERE vs.volunteer.id = :volunteerId")
    public List<Skill> findSkillsByVolunteerId(@Param("volunteerId") Integer volunteerId);

}
