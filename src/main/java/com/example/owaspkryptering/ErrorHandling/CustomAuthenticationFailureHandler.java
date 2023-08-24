package com.example.owaspkryptering.ErrorHandling;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        if (exception.getMessage().contains("Bad credentials")) {
            System.out.println("Bad credentials");
            failureHandler.setDefaultFailureUrl("/login?error=bad_credentials");
        } else if (exception.getMessage().contains("User is disabled")) {
            System.out.println("User is disabled");
            failureHandler.setDefaultFailureUrl("/login?error=user_disabled");
        } else {
            System.out.println("Generic error");
            failureHandler.setDefaultFailureUrl("/login?error=generic");
        }

        failureHandler.onAuthenticationFailure(request, response, exception);
    }
}

