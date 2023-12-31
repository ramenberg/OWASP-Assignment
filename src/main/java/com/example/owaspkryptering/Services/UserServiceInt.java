package com.example.owaspkryptering.Services;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.Role;
import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Repositories.RoleRepository;
import com.example.owaspkryptering.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceInt implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceInt(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        Role role = roleRepository.findByName("user");
        if (role == null) {
            role = checkRoleExists();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    private Role checkRoleExists() {
        Role role = new Role();
        role.setName("user");
        return roleRepository.save(role);
    }
}

