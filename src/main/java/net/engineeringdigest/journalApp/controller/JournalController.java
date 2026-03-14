package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/me/journals")
public class JournalController {

    private final JournalEntryService journalService;
    private final UserService userService;

    public JournalController(JournalEntryService journalService,
                             UserService userService) {
        this.journalService = journalService;
        this.userService = userService;
    }

    // CREATE JOURNAL FOR LOGGED-IN USER

    @PostMapping
    public JournalEntry createJournal(@RequestBody JournalEntry entry) {

        // 1️⃣ Get logged-in user email from Spring Security
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // 2️⃣ Load user from DB
        Optional<User> userOptional = userService.getUserByEmail(email);

        // 3️⃣ Save journal for that user
        if (userOptional.isPresent()) {
            entry.setUser(userOptional.get());
            return journalService.saveEntry(entry);
        }

        return null;
    }

    // =====================================================
    // GET ALL JOURNALS OF LOGGED-IN USER
    // =====================================================
    @GetMapping
    public List<JournalEntry> getMyJournals() {

        // 1️⃣ Get logged-in user email
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        // 2️⃣ Load user from DB
        Optional<User> userOptional = userService.getUserByEmail(email);

        // 3️⃣ Return only their journals
        return userOptional
                .map(User::getJournalEntries)
                .orElse(null);
    }

    // ================= SIMPLE QUERY METHODS ===============

    @GetMapping("/search/title")
    public List<JournalEntry> findByTitle(@RequestParam String title) {
        return journalService.getByTitle(title);
    }

    @GetMapping("/search/mood")
    public List<JournalEntry> findByMood(@RequestParam String mood) {
        return journalService.getByMood(mood);
    }

    @GetMapping("/search/public")
    public List<JournalEntry> findByPublic(@RequestParam boolean isPublic) {
        return journalService.getByPublic(isPublic);
    }

    @GetMapping("/search/wordcount")
    public List<JournalEntry> findByWordCount(@RequestParam int count) {
        return journalService.getByWordCount(count);
    }

    // ================= COMPLEX QUERY METHODS ==============

    @GetMapping("/search/mood-public")
    public List<JournalEntry> findByMoodAndPublic(
            @RequestParam String mood,
            @RequestParam boolean isPublic) {

        return journalService.getByMoodAndPublic(mood, isPublic);
    }

    @GetMapping("/search/mood-or-wordcount")
    public List<JournalEntry> findByMoodOrWordCount(
            @RequestParam String mood,
            @RequestParam int wordCount) {

        return journalService.getByMoodOrWordCount(mood, wordCount);
    }

    @GetMapping("/search/wordcount-range")
    public List<JournalEntry> findByWordCountRange(
            @RequestParam int min,
            @RequestParam int max) {

        return journalService.getByWordCountRange(min, max);
    }

    @GetMapping("/search/title-contains")
    public List<JournalEntry> searchByTitle(
            @RequestParam String keyword) {

        return journalService.searchByTitle(keyword);
    }

    @GetMapping("/search/public-sorted")
    public List<JournalEntry> getPublicSorted(
            @RequestParam boolean isPublic) {

        return journalService.getPublicSortedByDate(isPublic);
    }
}