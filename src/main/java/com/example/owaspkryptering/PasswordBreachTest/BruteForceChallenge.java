package com.example.owaspkryptering.PasswordBreachTest;

import com.example.owaspkryptering.Repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BruteForceChallenge {

    @Autowired
    private UserRepository userRepository; // Anpassa detta beroende på din kodstruktur

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final String targetUsername = "targetUser";
    private final String targetPassword = "targetPassword"; // Det verkliga lösenordet

    @PostConstruct
    public void runBruteForce() {
        // Brute force loop
        for (int passwordLength = 1; passwordLength <= 10; passwordLength++) {
            generateAndTestPasswords("", passwordLength);
        }
    }

    private void generateAndTestPasswords(String base, int length) {
        if (length == 0) {
            String currentPassword = base;
            String hashedPassword = passwordEncoder.encode(currentPassword);

            if (hashedPassword.equals(userRepository.getHashedPasswordForUsername(targetUsername))) {
                System.out.println("Password cracked: " + currentPassword);
            }
            return;
        }

        for (char c = 'a'; c <= 'z'; c++) {
            generateAndTestPasswords(base + c, length - 1);
        }
    }
}
