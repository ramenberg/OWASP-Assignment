package com.example.owaspkryptering.Services;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.Role;
import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Repositories.RoleRepository;
import com.example.owaspkryptering.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceInt implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceInt.class);
    private final PasswordEncoder passwordEncoder;


    public UserServiceInt(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        UserDto userDtoWithEncryptedPassword = new UserDto(userDto.getEmail(), userDto.getPassword());
        userDtoWithEncryptedPassword.encryptPassword(passwordEncoder);

        user.setPassword(userDtoWithEncryptedPassword.getPassword());

        Role role = roleRepository.findByName("user");
        if (role == null) {
            role = checkRoleExists();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        logger.debug("findByEmail result for email: " + email + " is: " + user);
        return user;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        logger.debug("findUserByEmail result for email: " + email + " is: " + user);
        return user;
    }

    // För att visa alla användare
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
    private Role checkRoleExists() {
        logger.debug("Role does not exist, creating new role");
        Role role = new Role();
        role.setName("user");
        return roleRepository.save(role);
    }
}

