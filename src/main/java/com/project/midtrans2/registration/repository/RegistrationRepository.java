package com.project.midtrans2.registration.repository;

import com.project.midtrans2.registration.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByEmail(String email);
    Optional<Registration> findByVerificationToken(String token);
}