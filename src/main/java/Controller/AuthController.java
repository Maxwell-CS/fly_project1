package org.example.flight_project.controller;

import jakarta.validation.Valid;
import org.example.flight_project.dto.LoginDTO;
import org.example.flight_project.dto.LoginResponseDTO;
import org.example.flight_project.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
