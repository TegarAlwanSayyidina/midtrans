package com.project.midtrans2.transactionlist.model;

import java.math.BigDecimal;

public class CreditCardDetails {
    private String creditCardNumber;
    private String issuingBank;
    private String secure3D;
    private String fdsRecommendation;
    private String approvalCode;
    private String bankResponse;
    private String issuingBrand;
    private String installmentTerm;
    private String installmentType;
    private BigDecimal redeemedAmount;
    private Integer redeemedPoints;
    private String twoClick;
    private String oneClick;
    private String retrievalReferenceNumber;
    private String preAuthorize;
    private String cardType;
    private String bankMid;
    private String cardChannel;

    // Getters dan Setters

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public String getBankMid() {
        return bankMid;
    }

    public void setBankMid(String bankMid) {
        this.bankMid = bankMid;
    }

    public String getBankResponse() {
        return bankResponse;
    }

    public void setBankResponse(String bankResponse) {
        this.bankResponse = bankResponse;
    }

    public String getCardChannel() {
        return cardChannel;
    }

    public void setCardChannel(String cardChannel) {
        this.cardChannel = cardChannel;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getFdsRecommendation() {
        return fdsRecommendation;
    }

    public void setFdsRecommendation(String fdsRecommendation) {
        this.fdsRecommendation = fdsRecommendation;
    }

    public String getInstallmentTerm() {
        return installmentTerm;
    }

    public void setInstallmentTerm(String installmentTerm) {
        this.installmentTerm = installmentTerm;
    }

    public String getInstallmentType() {
        return installmentType;
    }

    public void setInstallmentType(String installmentType) {
        this.installmentType = installmentType;
    }

    public String getIssuingBank() {
        return issuingBank;
    }

    public void setIssuingBank(String issuingBank) {
        this.issuingBank = issuingBank;
    }

    public String getIssuingBrand() {
        return issuingBrand;
    }

    public void setIssuingBrand(String issuingBrand) {
        this.issuingBrand = issuingBrand;
    }

    public String getOneClick() {
        return oneClick;
    }

    public void setOneClick(String oneClick) {
        this.oneClick = oneClick;
    }

    public String getPreAuthorize() {
        return preAuthorize;
    }

    public void setPreAuthorize(String preAuthorize) {
        this.preAuthorize = preAuthorize;
    }

    public BigDecimal getRedeemedAmount() {
        return redeemedAmount;
    }

    public void setRedeemedAmount(BigDecimal redeemedAmount) {
        this.redeemedAmount = redeemedAmount;
    }

    public Integer getRedeemedPoints() {
        return redeemedPoints;
    }

    public void setRedeemedPoints(Integer redeemedPoints) {
        this.redeemedPoints = redeemedPoints;
    }

    public String getRetrievalReferenceNumber() {
        return retrievalReferenceNumber;
    }

    public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
        this.retrievalReferenceNumber = retrievalReferenceNumber;
    }

    public String getSecure3D() {
        return secure3D;
    }

    public void setSecure3D(String secure3D) {
        this.secure3D = secure3D;
    }

    public String getTwoClick() {
        return twoClick;
    }

    public void setTwoClick(String twoClick) {
        this.twoClick = twoClick;
    }
}
