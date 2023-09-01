package com.example.owaspkryptering.Controllers;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Security.CustomUserDetailsService;
import com.example.owaspkryptering.Services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final CustomUserDetailsService customUserDetailsService;

    UserDetailsService userDetailsService;


    public AuthController(UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/index";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('admin')")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

//    @PostMapping(value = "/login", headers = "Accept=application/json")
//    public String login(@ModelAttribute("user") UserDto userDto, Model model) {
//        logger.info("Login attempt with email: {}", userDto.getEmail() + " and password: " + userDto.getPassword());
//        try {
//            User user = userService.findUserByEmail(userDto.getEmail());
//
//            if (user != null && passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
//                model.addAttribute("user", user);
//                return "redirect:/welcome";
//            } else {
//                model.addAttribute("error", "Felaktigt användarnamn eller lösenord");
//                return "login";
//            }
//        } catch (Exception e) {
//            model.addAttribute("error", "Ett fel uppstod under inloggningen");
//            return "login";
//        }
//    }

//    @PostMapping(value = "/login", headers = "Accept=application/json")
//    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
//        logger.info("Login attempt with username: {} and password: {}", username, password);
//        try {
//            User user = userService.findUserByEmail(username);
//
//            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//                model.addAttribute("user", user);
//                return "redirect:/welcome";
//            } else {
//                model.addAttribute("error", "Felaktigt användarnamn eller lösenord");
//                return "login";
//            }
//        } catch (Exception e) {
//            model.addAttribute("error", "Ett fel uppstod under inloggningen");
//            return "login";
//        }
//    }

    @PostMapping(value = "/login", headers = "Accept=application/json")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, UserDto userDto) {
        try {
            logger.info("Login attempt with username: {} and password: {}", username, password);
//            User userDetails = userService.findUserByEmail(userDto.getEmail());
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            logger.info("From /login: Login attempt with username/email: {} and password: {}", userDetails.getUsername(), userDetails.getPassword());
//            logger.info("From /login: Login attempt with username/email: {} and password: {}", userDetails.getEmail(), userDetails.getPassword());

            if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
                logger.info("/login: logging in to welcome");
                model.addAttribute("user", userDetails);
                return "redirect:/welcome";
            } else {
                logger.info("/login: login failed bad credentials");
                model.addAttribute("error", "Felaktigt användarnamn eller lösenord");
                return "login";
            }
        } catch (Exception e) {
            logger.info("/login: login failed fel");
            model.addAttribute("error", "Ett fel uppstod under inloggningen");
            return "login";
        }
    }
}
