package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
