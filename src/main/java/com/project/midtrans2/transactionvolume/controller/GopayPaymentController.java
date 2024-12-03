package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.GopayPayment;
import com.project.midtrans2.transactionvolume.service.GopayPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("http://localhost:4200")
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
        LocalDate today = LocalDate.now();
        return service.getPaymentsForPeriod(today, today.plusDays(1));
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<GopayPayment> getPaymentsLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<GopayPayment> getPaymentsLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Ini"
    @GetMapping("/this-month")
    public List<GopayPayment> getPaymentsThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return service.getPaymentsForPeriod(startDate, endDate);
    }
}
