package com.project.midtrans2.profile.service;

import com.project.midtrans2.profile.model.Profile;
import com.project.midtrans2.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    public Profile updateProfile(Long id, Profile updatedProfile) {
        return profileRepository.findById(id).map(existingProfile -> {
            existingProfile.setFullName(updatedProfile.getFullName());
            existingProfile.setEmailAddress(updatedProfile.getEmailAddress());
            existingProfile.setPhoneNumber(updatedProfile.getPhoneNumber());
            existingProfile.setUserRole(updatedProfile.getUserRole());
            existingProfile.setPassword(updatedProfile.getPassword());
            return profileRepository.save(existingProfile);
        }).orElseThrow(() -> new IllegalArgumentException("Profile with id " + id + " not found"));
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }
}
