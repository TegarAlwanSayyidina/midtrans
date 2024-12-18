package com.project.midtrans2.login.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private final String secretKey = "yCwH2h7ptpFDg1l2hPbtYZJrYZmyZxYXh/yAB/OdTKQ="; // Harus rahasia
    private final long tokenExpiration = 86400000; // 1 hari dalam milidetik
    private final long resetTokenExpiration = 3600000; // 1 jam dalam milidetik
    private final long verificationTokenExpiration = 86400000; // 1 hari untuk token verifikasi

    // Generate token JWT umum
    public String generateToken(String email) {
        return createToken(email, tokenExpiration);
    }

    // Generate token untuk reset password
    public String generateResetToken(String email) {
        return createToken(email, resetTokenExpiration);
    }

    // Generate token untuk verifikasi email
    public String generateVerificationToken(String email) {
        return createToken(email, verificationTokenExpiration);
    }

    private String createToken(String subject, long expirationTime) {
        return Jwts.builder()
                .setSubject(subject) // Menyimpan email sebagai subjek
                .setIssuedAt(new Date()) // Tanggal pembuatan token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Tanggal kedaluwarsa token
                .signWith(SignatureAlgorithm.HS256, secretKey) // Menandatangani token dengan algoritma HS256
                .compact();
    }

    // Mengekstrak email dari token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Mengekstrak klaim tertentu dari token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // Kunci untuk memvalidasi token
                .parseClaimsJws(token)
                .getBody();
    }

    // Mengecek apakah token sudah kedaluwarsa
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}