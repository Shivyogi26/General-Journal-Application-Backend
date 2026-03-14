package net.engineeringdigest.journalApp.entity;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // ⭐ LOGIN IDENTIFIER (must be unique)
    @Column(nullable = false, unique = true)
    private String email;

    // ⭐ PASSWORD (will be stored hashed later)
    @Column(nullable = false)
    private String password;

    // ⭐ ROLE FOR AUTHORIZATION
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // =========================
    // OneToOne Relationship (User → Profile)
    // =========================
    @JsonManagedReference(value = "user-profile")
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Profile profile;

    // =========================
    // OneToMany Relationship (User → JournalEntries)
    // =========================
    @JsonManagedReference(value = "user-journal")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<JournalEntry> journalEntries;

    public User() {}

    // ⭐ Updated constructor (includes password + role)
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // =========================
    // Getters & Setters
    // =========================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }
}
