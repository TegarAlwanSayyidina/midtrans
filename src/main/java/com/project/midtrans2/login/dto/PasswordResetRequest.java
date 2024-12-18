package com.project.midtrans2.login.dto;

public class PasswordResetRequest {
    private String email;
    private String newPassword;
    private String confirmNewPassword;

    public PasswordResetRequest() {}

    public PasswordResetRequest(String email, String newPassword, String confirmNewPassword) {
        this.email = email;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    // Validasi password baru dan konfirmasi password
    public boolean isPasswordValid() {
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }
}