package com.example.owaspkryptering.Security;
import com.example.owaspkryptering.Models.Role;
import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        logger.info("loadUserByUsername result for email: " + email + " is: " + user.getEmail());
        if (user != null) {
            logger.info("loadUserByUsername user is not null");
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}

//@Service
//public class CustomUserDetailsService {
//
//    private final UserRepository userRepository;
//
//    Logger logger = Logger.getLogger(CustomUserDetailsService.class.getName());
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User loadUserByUsername(String email) throws UsernameNotFoundException {
//        logger.info("loadUserByUsername result for email: " + email);
//        User user = userRepository.findByEmail(email);
//        if (user != null) {
//            return user;
//        } else {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//    }
//}

