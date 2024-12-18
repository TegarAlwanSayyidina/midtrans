package com.project.midtrans2.login.repository;

import com.project.midtrans2.login.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    // Method tambahan untuk pengecekan email
    boolean existsByEmail(String email);

    // Method untuk mencari pengguna berdasarkan email
    Optional<Login> findByEmail(String email);

    // Method untuk mencari pengguna berdasarkan token verifikasi
    Optional<Login> findByVerificationToken(String token);

    // Method untuk mencari pengguna berdasarkan token reset password
    Optional<Login> findByResetPasswordToken(String token);
}