package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.Profile;
import net.engineeringdigest.journalApp.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
