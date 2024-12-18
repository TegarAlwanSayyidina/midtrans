package com.project.midtrans2.registration.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String businessLegality;

    private String verificationToken;

    @Column(name = "active") // Menandakan apakah akun sudah aktif
    private boolean active = false;

    @Column(name = "account_activated") // Menandakan apakah akun sudah diverifikasi
    private boolean accountActivated = false;

    private LocalDateTime tokenCreationTime; // Waktu pembuatan token untuk verifikasi

    // Konstruktor default jika diperlukan
    public Registration() {}

    // Getter dan Setter bisa dihasilkan otomatis oleh @Data atau ditambahkan sesuai kebutuhan
}