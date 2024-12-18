package com.project.midtrans2.registration.service;

import com.project.midtrans2.registration.dto.RegistrationRequest;
import com.project.midtrans2.registration.model.Registration;
import com.project.midtrans2.registration.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private static final long TOKEN_EXPIRATION_HOURS = 24; // Token berlaku selama 24 jam
    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);  // Menggunakan Logger

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationEmailService registrationEmailService;

    public String register(RegistrationRequest request) {
        try {
            // Cek apakah email sudah terdaftar
            Optional<Registration> existingUser = registrationRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                return "Email is already registered!";
            }

            // Membuat entitas pengguna baru
            Registration registration = new Registration();
            registration.setFullName(request.getFullName());
            registration.setEmail(request.getEmail());
            registration.setPassword(passwordEncoder.encode(request.getPassword()));
            registration.setPhoneNumber(request.getPhoneNumber());
            registration.setBusinessLegality(request.getBusinessLegality());

            // Membuat token verifikasi
            String token = UUID.randomUUID().toString();
            registration.setVerificationToken(token);

            // Simpan waktu pembuatan token
            registration.setTokenCreationTime(LocalDateTime.now());

            // Simpan pengguna ke database
            registrationRepository.save(registration);

            // Kirim email verifikasi
            registrationEmailService.sendVerificationEmail(registration.getEmail(), token);

            logger.info("User registered successfully: {}", request.getEmail());
            return "Registration successful! Please check your email for account verification.";

        } catch (Exception e) {
            logger.error("Error during registration: ", e);
            return "Registration failed due to server error. Please try again later.";
        }
    }

    public String verifyAccount(String token) {
        Optional<Registration> userOptional = registrationRepository.findByVerificationToken(token);
        if (userOptional.isPresent()) {
            Registration registration = userOptional.get();

            // Periksa apakah token telah kedaluwarsa
            LocalDateTime tokenCreationTime = registration.getTokenCreationTime();
            LocalDateTime expirationTime = tokenCreationTime.plusHours(TOKEN_EXPIRATION_HOURS);
            if (LocalDateTime.now().isAfter(expirationTime)) {
                logger.warn("Token has expired: {}", token);
                return "Token has expired!";
            }

            if (registration.isActive() && registration.isAccountActivated()) {
                return "Account is already activated!";
            }

            // Perbarui status akun
            registration.setActive(true);  // Set akun aktif
            registration.setAccountActivated(true);  // Set akun telah diverifikasi
            registration.setVerificationToken(null);  // Hapus token verifikasi
            registrationRepository.save(registration);

            logger.info("User account activated successfully: {}", registration.getEmail());
            return "Account activated successfully!";
        }

        logger.warn("Invalid or expired token: {}", token);
        return "Invalid token!";
    }
}