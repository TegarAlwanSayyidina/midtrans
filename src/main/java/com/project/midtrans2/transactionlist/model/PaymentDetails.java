package com.project.midtrans2.transactionlist.model;

import java.time.LocalDateTime;

public class PaymentDetails {
    private LocalDateTime transactionTime;
    private LocalDateTime settlementTime;
    private LocalDateTime expiryTime;
    private String customField;
    private String popName;
    private String paymentProviderReferenceId;
    private String invoiceId;

    // Getters dan Setters

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPaymentProviderReferenceId() {
        return paymentProviderReferenceId;
    }

    public void setPaymentProviderReferenceId(String paymentProviderReferenceId) {
        this.paymentProviderReferenceId = paymentProviderReferenceId;
    }

    public String getPopName() {
        return popName;
    }

    public void setPopName(String popName) {
        this.popName = popName;
    }

    public LocalDateTime getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(LocalDateTime settlementTime) {
        this.settlementTime = settlementTime;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}

