package com.project.midtrans2.transaciton.service;

import com.project.midtrans2.transaciton.model.PaymentLink;
import com.project.midtrans2.transaciton.repository.PaymentLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentLinkService {

    @Autowired
    private PaymentLinkRepository paymentLinkRepository;

    // Retrieve all payment links
    public List<PaymentLink> getAllPaymentLinks() {
        return paymentLinkRepository.findAll();
    }

    // Create a new payment link
    public PaymentLink createPaymentLink(PaymentLink paymentLink) {
        return paymentLinkRepository.save(paymentLink);
    }

    // Retrieve payment links by Order ID
    public List<PaymentLink> getPaymentLinksByOrderId(String orderId) {
        return paymentLinkRepository.findByOrderId(orderId);
    }

    // Bulk upload payment links
    public String bulkUploadPaymentLinks(List<PaymentLink> paymentLinks) {
        for (PaymentLink paymentLink : paymentLinks) {
            paymentLink.setDateCreated(java.time.LocalDateTime.now());
            paymentLinkRepository.save(paymentLink);
        }
        return "Bulk upload completed";
    }
}
