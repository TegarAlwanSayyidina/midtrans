package com.project.midtrans2.paymentreminder.controller;

import com.project.midtrans2.paymentreminder.model.PaymentReminder;
import com.project.midtrans2.paymentreminder.service.PaymentReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/payment-reminder")
public class PaymentReminderController {

    private final PaymentReminderService paymentReminderService;

    @Autowired
    public PaymentReminderController(PaymentReminderService paymentReminderService) {
        this.paymentReminderService = paymentReminderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPaymentReminder(@RequestBody PaymentReminder paymentReminder) {
        PaymentReminder savedPaymentReminder = paymentReminderService.createPaymentReminder(paymentReminder);
        return ResponseEntity.ok("Payment reminder created with ID: " + savedPaymentReminder.getId());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PaymentReminder> getPaymentReminder(@PathVariable Long id) {
        return paymentReminderService.getPaymentReminderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PaymentReminder>> getAllPaymentReminders() {
        List<PaymentReminder> reminders = paymentReminderService.getAllPaymentReminders();
        return ResponseEntity.ok(reminders);
    }
}
