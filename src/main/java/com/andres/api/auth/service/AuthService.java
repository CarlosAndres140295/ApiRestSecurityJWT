package com.andres.api.auth.service;

import com.andres.api.user.dto.AuthResponse;
import com.andres.api.user.dto.LoginRequest;
import com.andres.api.user.dto.RegisterRequest;
import com.andres.api.user.model.Role;
import com.andres.api.user.model.User;
import com.andres.api.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user =repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        String token = jwtService.getToken(user);

        return AuthResponse.builder().token(token).build();
    }
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .build();

        Optional<User> validUsername = repository.findByUsername(user.getUsername());

        if (validUsername.isEmpty()){
            user = repository.save(user);
        }else{
            throw new UsernameNotFoundException("Username not found");
        }

        return AuthResponse.builder().token(jwtService.getToken(user)).build();
    }



}
