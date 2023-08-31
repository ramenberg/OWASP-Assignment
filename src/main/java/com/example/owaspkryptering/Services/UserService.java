package com.example.owaspkryptering.Services;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    List<UserDto> findAllUsers();

    User findUserByEmail(String email);
}
