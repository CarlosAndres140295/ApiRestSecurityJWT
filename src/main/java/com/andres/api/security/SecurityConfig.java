package com.andres.api.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()); // Deshabilitar el csrf
        http.formLogin(login -> login.disable()); // Deshabitar el formulario
        http.authorizeHttpRequests( auth -> {
            auth.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll(); // Quitamos la seguridad en las rutas auth
            auth.requestMatchers( "/api/products/**").permitAll(); // Se puede especificar el metodo a permitir o que permita todos
            auth.anyRequest().authenticated(); // Protegemos las demas rutas
        }).sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Declaramos la politicas de session sin ESTADO
                                                                                    // para que se envie el token en casa request
                });
        http.authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
