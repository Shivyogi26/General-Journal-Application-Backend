package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.dto.LoginRequestDTO;
import net.engineeringdigest.journalApp.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String authenticateUser(LoginRequestDTO loginRequest) {

        // Step 1 — authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Step 2 — generate JWT token after successful authentication
        return jwtService.generateToken(loginRequest.getEmail());
    }
}