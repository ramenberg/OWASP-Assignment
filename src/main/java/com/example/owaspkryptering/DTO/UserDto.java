package com.example.owaspkryptering.DTO;

import com.example.owaspkryptering.Models.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
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

//    public UserDto(String email, String password, PasswordEncoder passwordEncoder) {
//        this.email = email;
//        this.password = passwordEncoder.encode(password);
//    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
    }
}
