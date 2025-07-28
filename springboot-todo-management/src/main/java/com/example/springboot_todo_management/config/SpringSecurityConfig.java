package com.example.springboot_todo_management.config;

import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Getter
    private enum Role {
        ADMIN("ADMIN"),
        USER("USER");

        private final String value;

        Role(String value) {
            this.value = value;
        }

    }

    private static final String URL = "/api/**";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers(HttpMethod.POST, URL).hasRole(Role.ADMIN.getValue());
                    authorize.requestMatchers(HttpMethod.PUT, URL).hasRole(Role.ADMIN.getValue());
                    authorize.requestMatchers(HttpMethod.DELETE, URL).hasRole(Role.ADMIN.getValue());
                    authorize.requestMatchers(HttpMethod.GET, URL).hasAnyRole(Role.ADMIN.getValue(), Role.USER.getValue());
                    authorize.requestMatchers(HttpMethod.PATCH, URL).hasAnyRole(Role.ADMIN.getValue(), Role.USER.getValue());
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("password1"))
                .roles(Role.USER.getValue())
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(Role.ADMIN.getValue())
                .build();

        return new InMemoryUserDetailsManager(user1, admin);
    }
}
