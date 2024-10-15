package com.project.midtrans2.paymentlink.controller;

import com.project.midtrans2.paymentlink.model.PaymentLink;
import com.project.midtrans2.paymentlink.service.PaymentLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payment-links")
public class PaymentLinkController {

    @Autowired
    private PaymentLinkService paymentLinkService;

    // Get all payment links
    @GetMapping("/all")
    public List<PaymentLink> getAllPaymentLinks() {
        return paymentLinkService.getAllPaymentLinks();
    }

    // Get payment links by order ID
    @GetMapping("/search")
    public List<PaymentLink> getPaymentLinksByOrderId(@RequestParam String orderId) {
        return paymentLinkService.getPaymentLinksByOrderId(orderId);
    }

    // Create a new payment link
    @PostMapping("/create")
    public PaymentLink createPaymentLink(@RequestBody PaymentLink paymentLink) {
        paymentLink.setDateCreated(LocalDateTime.now());
        return paymentLinkService.createPaymentLink(paymentLink);
    }

    // Bulk upload payment links
    @PostMapping("/bulk-upload")
    public String bulkUploadPaymentLinks(@RequestBody List<PaymentLink> paymentLinks) {
        return paymentLinkService.bulkUploadPaymentLinks(paymentLinks);
    }
}

