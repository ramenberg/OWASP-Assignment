package com.example.owaspkryptering.PasswordBreachTest;

import com.example.owaspkryptering.Models.User;
import com.example.owaspkryptering.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class PasswordBreach {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = Logger.getLogger(PasswordBreach.class.getName());
    @Autowired
    private UserRepository userRepository;

    public PasswordBreach(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean attemptLogin(String email, String password) {
        User user = userRepository.findByEmail(email);

        if(user != null && passwordEncoder.matches(password, user.getPassword())) {
            logger.info("Login successful for email: " + email);
            return true;
        } else {
            logger.info("Login failed for email: " + email);
            return false;
        }
    }

    public void runBruteForce() {
        String targetEmail = "anv@test.se";
        String targetPasswordHash = userRepository.findByEmail(targetEmail).getPassword();
        logger.info("Target email: " + targetEmail + " with password hash: " + targetPasswordHash);

        if (targetPasswordHash != null) {
            logger.info("Starting brute force attack...");
            bruteForcePasswords(targetPasswordHash, 2); // Testa upp till x tecken långa lösenord
        } else {
            logger.info("No password found for user with email: " + targetEmail);
        }
    }

    private void bruteForcePasswords(String targetPasswordHash, int maxLength) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        int strength = 10; // Använd samma styrka som när du krypterar lösenordet

        boolean cracked = false;

        for (int length = 1; length <= maxLength; length++) {
            if (cracked) {
                logger.info("Password cracked. Stopping brute force");
                break;
            }
            bruteForceRecursive("", characters, length, targetPasswordHash, strength);
        }
        if (!cracked) {
            logger.info("Brute force finished. Password not cracked");
        }
    }

    private boolean bruteForceRecursive(String currentPassword, String characters, int length, String targetPasswordHash, int strength) {
        if (length == 0) {
            String hashedPassword = passwordEncoder.encode(currentPassword);
            if (hashedPassword.equals(targetPasswordHash)) {
                logger.info("Password cracked: " + currentPassword);
                return true;
            }
            return false;
        }

        for (char c : characters.toCharArray()) {
            if(bruteForceRecursive(currentPassword + c, characters, length - 1, targetPasswordHash, strength)) {
                return true;
            }

        }
        return false;
    }
}

