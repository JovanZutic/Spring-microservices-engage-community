package org.example.volunteerservice.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdatePasswordDto {

    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,64}$",
            message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
