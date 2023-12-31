package com.example.owaspkryptering.Services;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
