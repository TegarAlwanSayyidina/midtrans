package com.project.midtrans2.cardacceptancerate.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity(name = "PaymentTypeCardAcceptanceRate" )
public class PaymentTypeCardAcceptanceRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String type;

    // Default constructor
    public PaymentTypeCardAcceptanceRate() {
    }

    // Constructor with arguments
    public PaymentTypeCardAcceptanceRate(LocalDate date, String type) {
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}