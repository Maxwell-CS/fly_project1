package DTOs;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank private String username;
    @Email @NotBlank private String email;
    @Pattern(regexp = "^[A-Z].*") private String firstName;
    @Pattern(regexp = "^[A-Z].*") private String lastName;
    @NotBlank @Size(min=8) @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$")
    private String password;
    private String role = "USER";
}