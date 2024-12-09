package com.project.midtrans2.summary.totaltransaction.model;

public class TotalTransactionResponse {

    private Long totalTransactions;
    private String description;

    public TotalTransactionResponse(Long totalTransactions, String description) {
        this.totalTransactions = totalTransactions;
        this.description = description;
    }

    // Getters and Setters
    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
