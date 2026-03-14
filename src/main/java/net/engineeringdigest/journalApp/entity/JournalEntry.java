package net.engineeringdigest.journalApp.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@Entity
@Table(name = "journal_entries")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    // =========================
    // EXTRA FIELDS FOR JPA QUERY METHODS
    // =========================
    private String mood;

    private LocalDate createdDate;

    // ✅ Correct boolean mapping
    @JsonProperty("isPublic")
    private boolean publicEntry;

    private int wordCount;

    // =========================
    // MANY → ONE OWNER SIDE
    // =========================
    @JsonBackReference(value = "user-journal")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // =========================
    // CONSTRUCTORS
    // =========================
    public JournalEntry() {}

    public JournalEntry(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // =========================
    // AUTO SET DATE BEFORE SAVE
    // =========================
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDate.now();
    }

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isPublicEntry() {
        return publicEntry;
    }

    public void setPublicEntry(boolean publicEntry) {
        this.publicEntry = publicEntry;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
