package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Profile;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.ProfileService;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users/{userId}/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    public ProfileController(ProfileService profileService,
                             UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    // CREATE PROFILE
    @PostMapping
    public Profile createProfile(@PathVariable Long userId,
                                 @RequestBody Profile profile) {

        Optional<User> userOptional = userService.getUserEntityById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            profile.setUser(user);
            user.setProfile(profile);

            return profileService.saveProfile(profile);
        }

        return null;
    }

    // GET PROFILE
    @GetMapping
    public Profile getProfile(@PathVariable Long userId) {

        // ⭐ IMPORTANT FIX HERE
        Optional<User> userOptional = userService.getUserEntityById(userId);

        return userOptional.map(User::getProfile).orElse(null);
    }
}
