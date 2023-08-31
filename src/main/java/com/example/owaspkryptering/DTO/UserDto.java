package com.example.owaspkryptering.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.*;

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

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
