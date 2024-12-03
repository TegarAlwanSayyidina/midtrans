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

    // Memastikan hanya satu data profile yang bisa diubah
    public Profile updateProfile(Long id, Profile updatedProfile) {
        // Pastikan hanya satu data profile yang ada, ID yang dimaksud adalah ID 1
        if (id != 1) {
            throw new IllegalArgumentException("Profile ID yang diizinkan hanya ID 1");
        }

        return profileRepository.findById(id).map(existingProfile -> {
            // Hanya password yang dapat diubah
            existingProfile.setPassword(updatedProfile.getPassword());
            return profileRepository.save(existingProfile);
        }).orElseThrow(() -> new IllegalArgumentException("Profile dengan id " + id + " tidak ditemukan"));
    }

    public void deleteProfile(Long id) {
        // Memastikan hanya profile dengan ID 1 yang bisa dihapus
        if (id != 1) {
            throw new IllegalArgumentException("Profile ID yang diizinkan hanya ID 1");
        }
        profileRepository.deleteById(id);
    }
}
