package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.GopayPayment;
import com.project.midtrans2.transactionvolume.service.GopayPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/gopay-payments")
public class GopayPaymentController {

    @Autowired
    private GopayPaymentService service;

    @PostMapping
    public GopayPayment createPayment(@RequestBody GopayPayment payment) {
        return service.save(payment);
    }

    @GetMapping
    public List<GopayPayment> getAllPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public GopayPayment getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        service.deletePayment(id);
    }

    // Endpoint untuk filter berdasarkan "Hari Ini"
    @GetMapping("/today")
    public List<GopayPayment> getPaymentsToday() {
        return service.getPaymentsForPeriod(
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay()
        );
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<GopayPayment> getPaymentsLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<GopayPayment> getPaymentsLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Ini"
    @GetMapping("/this-month")
    public List<GopayPayment> getPaymentsThisMonth() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }
}

