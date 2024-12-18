package com.project.midtrans2.login.service;

import com.project.midtrans2.login.dto.PasswordResetRequest;
import com.project.midtrans2.login.dto.LoginRequest;
import com.project.midtrans2.login.exception.CustomException;
import com.project.midtrans2.login.model.Login;
import com.project.midtrans2.login.repository.LoginRepository;
import com.project.midtrans2.login.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final LoginEmailService loginEmailService;
    private final JwtTokenUtil jwtTokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository, LoginEmailService loginEmailService, JwtTokenUtil jwtTokenUtil, BCryptPasswordEncoder passwordEncoder) {
        this.loginRepository = loginRepository;
        this.loginEmailService = loginEmailService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest authRequest) {
        Optional<Login> user = loginRepository.findByEmail(authRequest.getEmail());
        if (user.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            throw new CustomException("Invalid email or password");
        }

        if (!user.get().isActivateAccount()) {
            throw new CustomException("Account not activated. Please confirm your email.");
        }

        logger.info("User {} successfully logged in.", authRequest.getEmail());
        return jwtTokenUtil.generateToken(authRequest.getEmail());
    }

    public void sendResetPasswordEmail(String email) {
        Optional<Login> user = loginRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new CustomException("User not found");
        }

        String token = jwtTokenUtil.generateResetToken(email);
        loginEmailService.sendPasswordResetEmail(email, token);
        logger.info("Password reset email sent to {}", email);
    }

    public void resetPasswordWithToken(String token, PasswordResetRequest passwordResetRequest) {
        if (!passwordResetRequest.isPasswordValid()) {
            throw new CustomException("Passwords do not match");
        }

        String email = jwtTokenUtil.extractEmail(token);
        Optional<Login> login = loginRepository.findByEmail(email);

        if (login.isEmpty()) {
            throw new CustomException("User not found");
        }

        Login u = login.get();
        u.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        loginRepository.save(u);
        logger.info("Password successfully reset for user {}", email);
    }

    public void sendEmailVerification(Login user) {
        String token = jwtTokenUtil.generateVerificationToken(user.getEmail());

        String verificationUrl = String.format("%s/api/auth/confirm-email?token=%s",
                System.getenv("VERIFICATION_BASE_URL"), token);

        loginEmailService.sendEmail(user.getEmail(), "Please Confirm Your Email", verificationUrl);

        user.setVerificationToken(token);
        loginRepository.save(user);
        logger.info("Verification email sent to {}", user.getEmail());
    }

    public void confirmEmail(String token) {
        Optional<Login> user = loginRepository.findByVerificationToken(token);
        if (user.isEmpty()) {
            throw new CustomException("Invalid or expired token");
        }

        Login u = user.get();
        u.setActivateAccount(true);
        u.setVerificationToken(null);
        loginRepository.save(u);
        logger.info("Email successfully verified for user {}", u.getEmail());
    }
}