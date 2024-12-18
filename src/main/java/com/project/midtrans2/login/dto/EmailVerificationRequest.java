package com.project.midtrans2.login.dto;

import jakarta.validation.constraints.NotBlank;

public class EmailVerificationRequest {

    @NotBlank(message = "Token verifikasi tidak boleh kosong.")
    private String token;
    public EmailVerificationRequest() {}

    public EmailVerificationRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}