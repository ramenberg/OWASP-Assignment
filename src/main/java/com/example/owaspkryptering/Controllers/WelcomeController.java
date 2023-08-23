package com.example.owaspkryptering.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcomePage(String welcomeMessage, Model model) {
        model.addAttribute("welcomeMessage", welcomeMessage);
        return "welcome";
    }
}
