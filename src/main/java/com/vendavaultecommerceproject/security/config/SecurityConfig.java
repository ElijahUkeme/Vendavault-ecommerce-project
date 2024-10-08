package com.vendavaultecommerceproject.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->
                        req.requestMatchers("/api/v1/auth/**",
                                        "/notification/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/ws/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/","/static/**", "index*", "/css/*", "/js/*",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/main.css/",
                                        "/index.html/",
                                        "/main.js/",
                                        "/","/ws/**",
                                        "/confirm-account",
                                        "/download/{attachmentId}",
                                        "/return/{attachmentId}",
                                        "/api/bookings/**",
                                        "/retrieved/{productImageId}",
                                        "/activate-account")
                                .permitAll()
                                .requestMatchers("/video/**").hasAuthority("MANAGER")
                                .requestMatchers("/user/**").hasAuthority("MANAGER")
                                .requestMatchers("/seller/**").hasAuthority("MANAGER")
                                .requestMatchers("/sales/**").hasAuthority("MANAGER")
                                .requestMatchers("/products/**").hasAuthority("MANAGER")
                                .requestMatchers("/password/**").hasAuthority("MANAGER")
                                .requestMatchers("/admin/**").hasAuthority("MANAGER")
                                .requestMatchers("/category/**").hasAuthority("MANAGER")
                                .requestMatchers("/cart/**").hasAuthority("MANAGER")
                                .anyRequest().authenticated())
                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
