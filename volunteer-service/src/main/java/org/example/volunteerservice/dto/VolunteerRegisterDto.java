package org.example.volunteerservice.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VolunteerRegisterDto {

    private Integer id;
    private String name;
    private String email;
    private String bio;

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,64}$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
