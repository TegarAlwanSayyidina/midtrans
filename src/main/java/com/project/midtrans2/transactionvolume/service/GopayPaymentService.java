package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.GopayPayment;
import com.project.midtrans2.transactionvolume.repository.GopayPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class GopayPaymentService {

    @Autowired
    private GopayPaymentRepository repository;

    public GopayPayment save(GopayPayment payment) {
        return repository.save(payment);
    }

    public List<GopayPayment> getAllPayments() {
        return repository.findAll();
    }

    public GopayPayment getPaymentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }

    public List<GopayPayment> getPaymentsForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    // Logika untuk mendapatkan pembayaran "Hari Ini"
    public List<GopayPayment> getPaymentsToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        return repository.findByTransactionDateBetween(startOfDay, now);
    }

    // Logika untuk mendapatkan pembayaran "7 Hari Terakhir"
    public List<GopayPayment> getPaymentsLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime now = LocalDateTime.now();
        return repository.findByTransactionDateBetween(sevenDaysAgo, now);
    }

    // Logika untuk mendapatkan pembayaran "30 Hari Terakhir"
    public List<GopayPayment> getPaymentsLast30Days() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        LocalDateTime now = LocalDateTime.now();
        return repository.findByTransactionDateBetween(thirtyDaysAgo, now);
    }

    // Logika untuk mendapatkan pembayaran "Bulan Ini"
    public List<GopayPayment> getPaymentsThisMonth() {
        LocalDateTime startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        return repository.findByTransactionDateBetween(startOfMonth, now);
    }
}

