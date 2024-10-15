package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.QrisPayment;
import com.project.midtrans2.transactionvolume.service.QrisPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/qris-payments")
public class QrisPaymentController {

    @Autowired
    private QrisPaymentService service;

    @PostMapping
    public QrisPayment createPayment(@RequestBody QrisPayment payment) {
        return service.createPayment(payment);
    }

    @GetMapping("/{id}")
    public QrisPayment getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id);
    }

    @GetMapping
    public List<QrisPayment> getAllPayments() {
        return service.getAllPayments();
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable Long id) {
        service.deletePayment(id);
    }

    // Endpoint untuk filter berdasarkan "Hari Ini"
    @GetMapping("/today")
    public List<QrisPayment> getPaymentsForToday() {
        LocalDateTime startDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<QrisPayment> getPaymentsForLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<QrisPayment> getPaymentsForLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Lalu"
    @GetMapping("/last-month")
    public List<QrisPayment> getPaymentsForLastMonth() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }
}

