package com.project.midtrans2.transactionlist.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    // Menambahkan anotasi JsonFormat untuk mengubah format tanggal
    @Column(name = "date_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")  // Menampilkan hanya tanggal tanpa waktu
    private LocalDateTime dateTime;

    @PrePersist
    protected void onCreate() {
        this.dateTime = LocalDateTime.now();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", channel='" + channel + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", customerEmail='" + customerEmail + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
