package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.dto.UserRequestDTO;
import net.engineeringdigest.journalApp.dto.UserResponseDTO;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // =========================
    // CREATE USER
    // =========================
    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO user) {
        return userService.saveUser(user);
    }

    // =========================
    // GET ALL USERS (ADMIN ONLY)
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // =========================
    // GET USER BY ID (ADMIN ONLY)
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // =========================
    // DELETE USER (ADMIN ONLY)
    // =========================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}