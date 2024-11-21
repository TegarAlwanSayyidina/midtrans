package com.project.midtrans2.profile.controller;

import com.project.midtrans2.profile.model.PasswordChangeRequest;
import com.project.midtrans2.profile.model.UserProfile;
import com.project.midtrans2.profile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfile> updateProfile(
            @PathVariable Long userId,
            @RequestBody UserProfile updatedProfile) {
        UserProfile userProfile = userProfileService.updateProfile(userId, updatedProfile);
        return ResponseEntity.ok(userProfile);
    }

    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable Long userId,
            @RequestBody PasswordChangeRequest passwordChangeRequest) {
        boolean isChanged = userProfileService.changePassword(userId, passwordChangeRequest);
        if (isChanged) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update password");
        }
    }
}
