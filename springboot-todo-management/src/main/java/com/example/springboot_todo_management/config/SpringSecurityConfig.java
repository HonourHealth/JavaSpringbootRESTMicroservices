package com.example.springboot_todo_management.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Getter
//    private enum Role {
//        ADMIN("ADMIN"),
//        USER("USER");
//
//        private final String value;
//
//        Role(String value) {
//            this.value = value;
//        }
//
//    }

//    private static final String URL = "/api/**";
    private static final String AUTH_URL = "/api/auth/**";

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults()) // Enable CORS
                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers(HttpMethod.POST, URL).hasRole(Role.ADMIN.getValue());
//                    authorize.requestMatchers(HttpMethod.PUT, URL).hasRole(Role.ADMIN.getValue());
//                    authorize.requestMatchers(HttpMethod.DELETE, URL).hasRole(Role.ADMIN.getValue());
//                    authorize.requestMatchers(HttpMethod.GET, URL).hasAnyRole(Role.ADMIN.getValue(), Role.USER.getValue());
//                    authorize.requestMatchers(HttpMethod.PATCH, URL).hasAnyRole(Role.ADMIN.getValue(), Role.USER.getValue());
                    authorize.requestMatchers(AUTH_URL).permitAll();
                    authorize.requestMatchers("/api/todos/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("password1"))
//                .roles(Role.USER.getValue())
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles(Role.ADMIN.getValue())
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, admin);
//    }
}
