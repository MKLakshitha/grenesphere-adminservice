package com.greensphereadminportalservice.config;


import com.greensphereadminportalservice.service.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@Configuration

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //Uncomment this section to bypass authentication
    //comment the rest of the section
    //add a sample user then to check in UI

    /*
     * @Bean
     * public SecurityFilterChain securityFilterChain(HttpSecurity http)
     * throws Exception {
     * http
     * .authorizeHttpRequests(authorize -> authorize
     * .anyRequest().permitAll()) // Permit all requests
     * .csrf(csrf -> csrf.disable()); // Disable CSRF for easier testing
     * (optional)
     * 
     * return http.build();
     * }
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/error", "/css/**", "/vendors/**", "/images/**", "/js/**",
                                "/scss/**", "/fonts/**", "/gulpfile.js", "/package-lock.json", "/package.json")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/home", true)
                        .failureHandler(authenticationFailureHandler()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/access-denied"))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                    AuthenticationException exception) throws IOException, ServletException {
                if (exception instanceof DisabledException) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=disabled");
                } else if (exception instanceof BadCredentialsException) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=badcredentials");
                } else if (exception instanceof LockedException) {
                    getRedirectStrategy().sendRedirect(request, response, "/login?error=locked");
                } else {
                    super.onAuthenticationFailure(request, response, exception);
                }
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
        /*
     * @Bean
     * public UserDetailsService userDetailsService() {
     * return username -> {
     * usersModel user = userService.fetchByUsername(username);
     * if (user != null) {
     * return new User(user.getUsername(), user.getPassword(),
     * AuthorityUtils.createAuthorityList(user.getRole()));
     * } else {
     * throw new UsernameNotFoundException("User not found");
     * }
     * };
     * }
     */  
}
