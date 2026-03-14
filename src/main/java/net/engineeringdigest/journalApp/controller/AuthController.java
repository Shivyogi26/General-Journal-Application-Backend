package net.engineeringdigest.journalApp.controller;

import jakarta.validation.Valid;
import net.engineeringdigest.journalApp.dto.LoginRequestDTO;
import net.engineeringdigest.journalApp.dto.RegisterRequestDTO;
import net.engineeringdigest.journalApp.service.AuthService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService,
                          AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    // REGISTER USER
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequestDTO request) {

        userService.registerUser(request);

        return ResponseEntity.ok("User registered successfully");
    }

    // LOGIN USER

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {

        String token = authService.authenticateUser(loginRequest);

        return ResponseEntity.ok(Map.of("token", token));
    }
}