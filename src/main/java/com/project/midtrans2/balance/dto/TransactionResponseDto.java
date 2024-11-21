package com.project.midtrans2.balance.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public class TransactionResponseDto {

    @NotNull(message = "Transaction date and time cannot be null")
    private LocalDateTime dateTime;

    @NotNull(message = "Order ID cannot be null")
    private String orderId;

    @NotNull(message = "Transaction type cannot be null")
    private String transactionType;

    @NotNull(message = "Payment channel cannot be null")
    private String channel;

    @NotNull(message = "Status cannot be null")
    private String status;

    @NotNull(message = "Amount cannot be null")
    private Integer amount;

    private Integer fee;

    // Constructor
    public TransactionResponseDto(LocalDateTime dateTime, String orderId, String transactionType, String channel, String status, Integer amount, Integer fee) {
        this.dateTime = dateTime;
        this.orderId = orderId;
        this.transactionType = transactionType;
        this.channel = channel;
        this.status = status;
        this.amount = amount;
        this.fee = fee;
    }

    // Getters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getChannel() {
        return channel;
    }

    public String getStatus() {
        return status;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getFee() {
        return fee;
    }
}