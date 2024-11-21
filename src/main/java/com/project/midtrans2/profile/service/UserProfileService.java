package com.project.midtrans2.profile.service;

import com.project.midtrans2.profile.model.PasswordChangeRequest;
import com.project.midtrans2.profile.model.UserProfile;
import com.project.midtrans2.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserProfile updateProfile(Long userId, UserProfile updatedProfile) {
        UserProfile existingProfile = userProfileRepository.findById(userId).orElseThrow();
        existingProfile.setFullName(updatedProfile.getFullName());
        existingProfile.setEmailAddress(updatedProfile.getEmailAddress());
        existingProfile.setPhoneNumber(updatedProfile.getPhoneNumber());
        existingProfile.setUserRole(updatedProfile.getUserRole());
        return userProfileRepository.save(existingProfile);
    }

    public boolean changePassword(Long userId, PasswordChangeRequest passwordChangeRequest) {
        UserProfile user = userProfileRepository.findById(userId).orElseThrow();
        if (passwordEncoder.matches(passwordChangeRequest.getCurrentPassword(), user.getPassword())) {
            if (passwordChangeRequest.getNewPassword().equals(passwordChangeRequest.getNewPasswordConfirmation())) {
                user.setPassword(passwordEncoder.encode(passwordChangeRequest.getNewPassword()));
                userProfileRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
