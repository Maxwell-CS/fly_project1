package org.example.fly_project.controller;

import jakarta.validation.Valid;
import org.example.fly_project.dto.UserRegisterDTO;
import org.example.fly_project.dto.UserResponseDTO;
import org.example.fly_project.model.User;
import org.example.fly_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRegisterDTO dto) {
        User user = modelMapper.map(dto, User.class);
        User saved = userService.register(user);
        UserResponseDTO response = new UserResponseDTO();
        response.setId(saved.getId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        // Implementación opcional
        return ResponseEntity.ok().build();
    }
}
