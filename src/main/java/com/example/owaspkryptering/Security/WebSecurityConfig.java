//package com.example.owaspkryptering.Config;
//
//import com.example.owaspkryptering.ErrorHandling.CustomAuthenticationFailureHandler;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.management.MXBean;
//
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
//    public WebSecurityConfig(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
//        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
//    }
////
////    @Bean
////    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
////        UserDetails user = User.withUsername("user")
////                .password("password")
////                .roles("USER")
////                .build();
////        return new InMemoryUserDetailsManager(user);
////    }
//
//
////    @Bean
////    public static PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//// ...
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//        http.authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/index")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/users")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/welcome")).hasAnyRole("user", "admin")
//                        .anyRequest().permitAll())
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/welcome", true)
//                        .permitAll())
//                .logout(LogoutConfigurer::permitAll);
//        return http.build();
//    }
//
//
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http)
////            throws Exception {
////        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/register/**").permitAll()
////            .requestMatchers("/index").permitAll()
////            .requestMatchers("/users").hasRole("ADMIN"))
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .loginProcessingUrl("/login")
////                        .defaultSuccessUrl("/welcome")
////                        .permitAll())
////                .logout(LogoutConfigurer::permitAll);
////        return http.build();
////    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
////        String hashedPassword = passwordEncoder().encode("password");
//        UserDetails user =
//                User.builder()
//                        .username("admin")
////                        .password(hashedPassword)
//                        .password("admin")
//                        .roles("admin")
//                        .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}