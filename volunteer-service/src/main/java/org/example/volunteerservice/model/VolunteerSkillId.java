package org.example.volunteerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class VolunteerSkillId implements java.io.Serializable {
    private static final long serialVersionUID = -5031664859108914879L;
    @NotNull
    @Column(name = "volunteer_id", nullable = false)
    private Integer volunteerId;

    @NotNull
    @Column(name = "skill_id", nullable = false)
    private Integer skillId;

    public Integer getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Integer volunteerId) {
        this.volunteerId = volunteerId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VolunteerSkillId entity = (VolunteerSkillId) o;
        return Objects.equals(this.skillId, entity.skillId) &&
                Objects.equals(this.volunteerId, entity.volunteerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillId, volunteerId);
    }

}