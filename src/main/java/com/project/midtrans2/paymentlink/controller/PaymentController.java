package com.project.midtrans2.paymentlink.controller;

import com.project.midtrans2.paymentlink.model.TransactionLink;
import com.project.midtrans2.paymentlink.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.midtrans2.paymentlink.exception.PaymentLinkNotFoundException;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment-link")
    public ResponseEntity<Map<String, Object>> createPaymentLink(@RequestBody Map<String, Object> paymentLinkRequest) {
        try {
            // Menggunakan PaymentService untuk membuat PaymentLink
            Map<String, Object> response = paymentService.createPaymentLink(paymentLinkRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error creating payment link: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Error creating payment link: " + e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Map<String, Object>> getPaymentLinkByOrderId(@PathVariable String orderId) {
        try {
            TransactionLink paymentLink = paymentService.getPaymentLinkByOrderId(orderId);
            Map<String, Object> response = Map.of("paymentLink", paymentLink);
            return ResponseEntity.ok(response);
        } catch (PaymentLinkNotFoundException e) {
            logger.error("Error retrieving payment link: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error retrieving payment link: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred"));
        }
    }
}