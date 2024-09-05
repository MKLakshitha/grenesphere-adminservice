package com.greensphereadminportalservice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class PasswordEncryptionUtil {
    public static void main(String[] args) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication.getName();
        System.out.println(authenticatedUserId);
    }
}

/*
 * import com.greensphere.admin_portal_service.model.usersModel;
 * import com.greensphere.admin_portal_service.service.userService;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.context.annotation.Bean;
 * import org.springframework.context.annotation.Configuration;
 * import org.springframework.security.authentication.AuthenticationManager;
 * import org.springframework.security.authentication.AuthenticationProvider;
 * import
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 * import
 * org.springframework.security.config.annotation.authentication.configuration.
 * AuthenticationConfiguration;
 * import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity;
 * import org.springframework.security.core.authority.AuthorityUtils;
 * import org.springframework.security.core.userdetails.User;
 * import org.springframework.security.core.userdetails.UserDetailsService;
 * import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * import org.springframework.security.crypto.password.PasswordEncoder;
 * import org.springframework.security.web.SecurityFilterChain;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity
 * public class SecurityConfig {
 * 
 * @Autowired
 * private userService userService;
 * 
 * @Bean
 * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
 * Exception {
 * http
 * .authorizeHttpRequests(authorize -> authorize
 * .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
 * .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
 * .anyRequest().authenticated())
 * .formLogin(form -> form
 * .loginPage("/login").permitAll()
 * .defaultSuccessUrl("/admin/dashboard", true))
 * .logout(logout -> logout
 * .logoutUrl("/logout")
 * .logoutSuccessUrl("/login?logout").permitAll());
 * 
 * return http.build();
 * }
 * 
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
 * 
 * @Bean
 * public PasswordEncoder passwordEncoder() {
 * return new BCryptPasswordEncoder();
 * }
 * 
 * @Bean
 * public AuthenticationProvider authenticationProvider() {
 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 * authProvider.setUserDetailsService(userDetailsService());
 * authProvider.setPasswordEncoder(passwordEncoder());
 * return authProvider;
 * }
 * 
 * @Bean
 * public AuthenticationManager
 * authenticationManager(AuthenticationConfiguration authConfig) throws
 * Exception {
 * return authConfig.getAuthenticationManager();
 * }
 * }
 */