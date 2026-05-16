package org.example.flight_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank private String email;
    @NotBlank private String password;
}
