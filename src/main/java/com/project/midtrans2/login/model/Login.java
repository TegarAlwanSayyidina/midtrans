package com.project.midtrans2.login.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nama bisnis
    private String businessName;

    // Nama lengkap pemilik akun
    private String fullName;

    @Column(unique = true)
    private String email; // Email unik untuk setiap pengguna

    private String phoneNumber; // Nomor telepon pengguna

    private String password; // Kata sandi pengguna

    @Column(name = "active")
    private boolean active; // Menunjukkan apakah akun aktif

    @Column(name = "account_activated")
    private boolean activateAccount; // Menunjukkan apakah akun telah diaktifkan

    private String resetPasswordToken; // Token untuk reset kata sandi

    private String verificationToken; // Token untuk verifikasi akun

    // Konstruktor default
    public Login() {}

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActivateAccount() {
        return activateAccount;
    }

    public void setActivateAccount(boolean activateAccount) {
        this.activateAccount = activateAccount;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}