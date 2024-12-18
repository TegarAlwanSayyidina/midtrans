package com.project.midtrans2.login.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LoginEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    public LoginEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Mengirim email konfirmasi aktivasi akun
    public void sendConfirmationEmail(String toEmail, String token) {
        String subject = "Confirm Your Email";
        String text = "Please click the following link to confirm your account: "
                + "http://localhost:8080/api/auth/activate?token=" + token;

        sendEmail(toEmail, subject, text);
    }

    // Mengirim email untuk reset password
    public void sendPasswordResetEmail(String toEmail, String token) {
        String subject = "Password Reset Request";
        String text = "To reset your password, click the following link: "
                + "http://localhost:8080/api/auth/reset-password?token=" + token;

        sendEmail(toEmail, subject, text);
    }

    // Fungsi untuk mengirimkan email umum (ubah aksesibilitas menjadi public)
    public void sendEmail(String toEmail, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}