package com.example.owaspkryptering.Controllers;

import com.example.owaspkryptering.DTO.UserDto;
import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

        User existing = userService.findByEmail(userDto.getEmail());

        if (existing != null) {
            result.rejectValue("email", null, "Det finns redan en användare med den e-postadressen");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByEmail(userDto.getEmail());
        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            model.addAttribute("user", user);
            return "redirect:/welcome";
        } else {
            model.addAttribute("error", "Felaktigt användarnamn eller lösenord");
            return "login";
        }
    }
}
