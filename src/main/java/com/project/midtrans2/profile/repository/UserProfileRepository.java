package com.project.midtrans2.profile.repository;

import com.project.midtrans2.profile.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmailAddress(String emailAddress);
}
