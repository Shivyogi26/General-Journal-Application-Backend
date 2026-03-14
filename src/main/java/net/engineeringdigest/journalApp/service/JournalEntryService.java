package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;

    public JournalEntryService(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }

    // =====================================================
    // BASIC CRUD
    // =====================================================

    public JournalEntry saveEntry(JournalEntry entry) {
        return journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public void deleteEntry(Long id) {
        journalEntryRepository.deleteById(id);
    }

    // =====================================================
    // SIMPLE QUERY METHODS
    // =====================================================

    public List<JournalEntry> getByTitle(String title) {
        return journalEntryRepository.findByTitle(title);
    }

    public List<JournalEntry> getByMood(String mood) {
        return journalEntryRepository.findByMood(mood);
    }

    public List<JournalEntry> getByPublic(boolean isPublic) {
        return journalEntryRepository.findByPublicEntry(isPublic);
    }

    public List<JournalEntry> getByWordCount(int wordCount) {
        return journalEntryRepository.findByWordCount(wordCount);
    }

    public List<JournalEntry> getByUserId(Long userId) {
        return journalEntryRepository.findByUserId(userId);
    }

    // =====================================================
    // COMPLEX QUERY METHODS
    // =====================================================

    public List<JournalEntry> getByMoodAndPublic(String mood, boolean isPublic) {
        return journalEntryRepository.findByMoodAndPublicEntry(mood, isPublic);
    }

    public List<JournalEntry> getByMoodOrWordCount(String mood, int wordCount) {
        return journalEntryRepository.findByMoodOrWordCount(mood, wordCount);
    }

    public List<JournalEntry> getByWordCountRange(int min, int max) {
        return journalEntryRepository.findByWordCountBetween(min, max);
    }

    public List<JournalEntry> searchByTitle(String keyword) {
        return journalEntryRepository.findByTitleContaining(keyword);
    }

    public List<JournalEntry> getPublicSortedByDate(boolean isPublic) {
        return journalEntryRepository.findByPublicEntryOrderByCreatedDateDesc(isPublic);
    }
}
