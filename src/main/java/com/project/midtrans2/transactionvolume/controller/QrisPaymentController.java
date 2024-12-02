package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.QrisPayment;
import com.project.midtrans2.transactionvolume.service.QrisPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<QrisPayment> getPaymentsForLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<QrisPayment> getPaymentsForLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Lalu"
    @GetMapping("/last-month")
    public List<QrisPayment> getPaymentsForLastMonth() {
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }
}
