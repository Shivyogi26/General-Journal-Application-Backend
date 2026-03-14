package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    // ========================= SIMPLE QUERY METHODS

    List<JournalEntry> findByTitle(String title);

    List<JournalEntry> findByMood(String mood);

    List<JournalEntry> findByPublicEntry(boolean publicEntry);

    List<JournalEntry> findByWordCount(int wordCount);

    List<JournalEntry> findByUserId(Long userId);



    // ========================= COMPLEX QUERY METHODS

    List<JournalEntry> findByMoodAndPublicEntry(String mood, boolean publicEntry);

    List<JournalEntry> findByMoodOrWordCount(String mood, int wordCount);

    List<JournalEntry> findByWordCountBetween(int min, int max);

    List<JournalEntry> findByTitleContaining(String keyword);

    List<JournalEntry> findByPublicEntryOrderByCreatedDateDesc(boolean publicEntry);

    //Try this
    //List<JournalEntry> findByUser_Name(String name);
}
