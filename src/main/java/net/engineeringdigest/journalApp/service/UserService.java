package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.dto.UserRequestDTO;
import net.engineeringdigest.journalApp.dto.UserResponseDTO;
import net.engineeringdigest.journalApp.dto.RegisterRequestDTO;
import net.engineeringdigest.journalApp.entity.Role;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.exception.UserNotFoundException;
import net.engineeringdigest.journalApp.mapper.UserMapper;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;   // ⭐ IMPORT

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    // NEW FIELD
    private final PasswordEncoder passwordEncoder;

    // UPDATED CONSTRUCTOR (Spring injects encoder automatically)
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE USER
    public UserResponseDTO saveUser(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    // GET ALL USERS
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET USER BY ID (DTO for API)
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // ⭐ IMPORTANT — ENTITY for internal usage
    public Optional<User> getUserEntityById(Long id) {
        return userRepository.findById(id);
    }

    // DELETE USER
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // =====================================================
    //  REGISTER USER (PASSWORD NOW ENCODED)
    // =====================================================
    public User registerUser(RegisterRequestDTO request) {

        // 1.Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // 2️.Create new user entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // PASSWORD ENCODED HERE
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // 3️. Assign default role
        user.setRole(Role.ROLE_USER);

        // 4️. Save to database
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
