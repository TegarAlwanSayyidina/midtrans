package com.project.midtrans2.balance.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transaction_balance") // Sesuaikan nama tabel dengan database
public class TransactionBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")  // Sesuaikan dengan nama kolom di database
    private String orderId;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_channel")
    private String paymentChannel;

    @Column(name = "gross_amount")
    private Integer grossAmount;  // Gunakan Integer agar sesuai dengan tipe di database

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "created_date")
    private LocalDate createdDate;  // Ubah ke LocalDate

    // Getter dan Setter untuk semua field

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Integer getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Integer grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}