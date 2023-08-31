package com.example.owaspkryptering.Services;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.Role;
import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Repositories.RoleRepository;
import com.example.owaspkryptering.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceInt implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceInt.class);
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


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

          // Kryptera lösenordet innan du sparar det
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encryptedPassword);

        Role role = roleRepository.findByName("user");
        if (role == null) {
            role = checkRoleExists();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        logger.info("findUserByEmail result for email: " + email + " is: " + user.getEmail());
        return user;
    }

//    @Override
//    public User loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user != null) {
//            logger.info("loadUserByUsername user is not null");
//            return new User(
//                    user.getEmail(),
//                    user.getPassword(),
//                    user.getRoles());
//        } else {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//    }


    // För att visa alla användare
    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    private Role checkRoleExists() {
        logger.info("Role does not exist, creating new role");
        Role role = new Role();
        role.setName("user");
        return roleRepository.save(role);
    }
}

