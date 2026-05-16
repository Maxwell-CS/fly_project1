package org.example.flight_project.service;

import org.example.flight_project.dto.LoginDTO;
import org.example.flight_project.dto.LoginResponseDTO;
import org.example.flight_project.model.User;
import org.example.flight_project.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public LoginResponseDTO login(LoginDTO loginDto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = (User) auth.getPrincipal();
        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token);
    }
}
