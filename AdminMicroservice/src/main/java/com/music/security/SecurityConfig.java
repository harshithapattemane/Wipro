package com.music.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public pages (no JWT required)
                        .requestMatchers(
                                "/",
                                "/home",
                                "/admin/home",
                                "/admin",
                                "/admin/login",
                                "/admin/login/**",
                                "/admin/register",
                                "/admin/register/**",
                                "/admin/logout",
                                "/songs/ui/**",
                                "/error",
                                "/favicon.ico",
                                "/css/**", "/js/**", "/images/**"
                        ).permitAll()

                       
                        .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()

                        
                        .requestMatchers(HttpMethod.POST, "/songs/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/songs/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/songs/**").hasAuthority("ADMIN")

                       
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess ->
                        sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
