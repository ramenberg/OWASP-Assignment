package com.example.owaspkryptering.Controllers;

import com.example.owaspkryptering.PasswordBreachTest.PasswordBreach;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordBreachConfig {

    public PasswordBreachConfig(PasswordBreach passwordBreach) {
        passwordBreach.runBruteForce();

    }
}
