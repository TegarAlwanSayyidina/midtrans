package com.project.midtrans2.paymentlink.exception;

public class PaymentLinkNotFoundException extends RuntimeException {
    public PaymentLinkNotFoundException(String message) {
        super(message);
    }
}