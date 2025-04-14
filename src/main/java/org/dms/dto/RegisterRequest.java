package org.dms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dms.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "lastName is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    private UserRole role;

}
