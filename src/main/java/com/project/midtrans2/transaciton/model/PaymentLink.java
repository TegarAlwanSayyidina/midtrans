package com.project.midtrans2.transaciton.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PaymentLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;
    private String orderId;
    private String paymentUrl;
    private String action;

    public PaymentLink() {}

    public PaymentLink(LocalDateTime dateCreated, String orderId, String paymentUrl, String action) {
        this.dateCreated = dateCreated;
        this.orderId = orderId;
        this.paymentUrl = paymentUrl;
        this.action = action;
    }

    // Getter and Setter Lekkku

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
