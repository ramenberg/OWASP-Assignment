package com.example.owaspkryptering.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
