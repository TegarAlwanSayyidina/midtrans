package com.project.midtrans2.login.controller;

import com.project.midtrans2.login.dto.PasswordResetRequest;
import com.project.midtrans2.login.dto.LoginRequest;
import com.project.midtrans2.login.exception.CustomException;
import com.project.midtrans2.login.model.Login;
import com.project.midtrans2.login.repository.LoginRepository;
import com.project.midtrans2.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginRepository loginRepository;

    @Autowired
    private LoginService loginService;

    public LoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    // Endpoint untuk login

    @PostMapping("/login")

    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest authRequest, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().toString());
        }
        String token = loginService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    // Endpoint untuk mengirim email reset password
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        String email = passwordResetRequest.getEmail();
        loginService.sendResetPasswordEmail(email);
        return ResponseEntity.ok("Password reset email sent.");
    }

    // Endpoint untuk mereset password dengan token
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody PasswordResetRequest passwordResetRequest) {
        try {
            loginService.resetPasswordWithToken(token, passwordResetRequest);
            return ResponseEntity.ok("Password successfully reset.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint untuk mengirim email verifikasi
    @PostMapping("/send-verification-email")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody PasswordResetRequest passwordResetRequest) {
        String email = passwordResetRequest.getEmail();
        Optional<Login> login = loginRepository.findByEmail(email);
        if (login.isEmpty()) {
            return ResponseEntity.ok("Jika email terdaftar, email verifikasi akan dikirim.");
        }
        loginService.sendEmailVerification(login.get());
        return ResponseEntity.ok("Email verifikasi telah dikirim.");
    }

    // Endpoint untuk mengonfirmasi email dengan token
    @PostMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Token verifikasi tidak boleh kosong.");
        }
        try {
            // Mengonfirmasi email berdasarkan token
            loginService.confirmEmail(token);

            // Setelah konfirmasi berhasil, beri tahu pengguna bahwa mereka telah masuk dan akun mereka telah diverifikasi
            return ResponseEntity.ok("Akun Anda telah berhasil diverifikasi. Anda sekarang sudah masuk.");
        } catch (CustomException e) {
            // Jika token tidak valid atau sudah kadaluarsa
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}