package br.com.fiap.ecopontos.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain config(HttpSecurity http, AuthorizationFilter authFilter) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // Autorização de requisições
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers( "/**").permitAll()
                        .anyRequest().authenticated()
        );

        // Desabilitar a proteção de frame options para o H2 console
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        // Filtro de autenticação
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
