package com.pharma.pharmserv.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pharma.pharmserv.Filter.JwtAuthFilter;
import com.pharma.pharmserv.Filter.LoginRateLimitFilter;
import com.pharma.pharmserv.Filter.UserRateLimitFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        @Autowired
        private LoginRateLimitFilter loginRateLimitFilter;

        @Autowired
        private UserRateLimitFilter userRateLimitFilter;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.cors(cors -> cors.configurationSource(request -> {
                        var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
                        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                        corsConfig.setAllowedHeaders(List.of("*"));
                        corsConfig.setAllowCredentials(true);
                        return corsConfig;
                }))
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/ms",
                                                                "/ms/auth/login",
                                                                "/ms/user/create-new-user",
                                                                "/ms/docs",
                                                                "/ms/docs/**",
                                                                "/ms/swagger-ui/**",
                                                                "/ms/api-docs",
                                                                "/ms/api-docs/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(
                                                jwtAuthFilter,
                                                UsernamePasswordAuthenticationFilter.class)
                                .addFilterBefore(
                                                loginRateLimitFilter,
                                                JwtAuthFilter.class)
                                .addFilterAfter(
                                                userRateLimitFilter,
                                                JwtAuthFilter.class);

                return http.build();
        }
}
