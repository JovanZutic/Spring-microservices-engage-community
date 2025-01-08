package org.example.volunteerservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "volunteer_skill")
public class VolunteerSkill {
    @EmbeddedId
    private VolunteerSkillId id;

    @MapsId("volunteerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @MapsId("skillId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public VolunteerSkillId getId() {
        return id;
    }

    public void setId(VolunteerSkillId id) {
        this.id = id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

}