package com.example.owaspkryptering.DTO;

import com.example.owaspkryptering.Models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty(message = "Email måste anges")
    @Email
    private String email;

    @NotEmpty(message = "Lösenord måste anges")
    private String password;

    public UserDto(String email, String password, PasswordEncoder passwordEncoder) {
        this.email = email;
        this.password = passwordEncoder.encode(password);
    }

}
