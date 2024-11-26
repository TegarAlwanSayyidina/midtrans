package com.project.midtrans2.transactionlist.model;

import java.time.LocalDate;

public class DisbursementDetails {
    private String transferMethod;
    private LocalDate disbursementTime;
    private String receiverName;
    private String requestedBy;
    private String approvedBy;
    private String note;
    private String causeOfFailure;

    // Getters dan Setters

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCauseOfFailure() {
        return causeOfFailure;
    }

    public void setCauseOfFailure(String causeOfFailure) {
        this.causeOfFailure = causeOfFailure;
    }

    public LocalDate getDisbursementTime() {
        return disbursementTime;
    }

    public void setDisbursementTime(LocalDate disbursementTime) {
        this.disbursementTime = disbursementTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getTransferMethod() {
        return transferMethod;
    }

    public void setTransferMethod(String transferMethod) {
        this.transferMethod = transferMethod;
    }
}
