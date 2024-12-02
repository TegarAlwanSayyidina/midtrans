package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.GopayPayment;
import com.project.midtrans2.transactionvolume.repository.GopayPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    // Mendapatkan pembayaran berdasarkan periode
    public List<GopayPayment> getPaymentsForPeriod(LocalDate startDate, LocalDate endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    // Mendapatkan pembayaran untuk "Hari Ini"
    public List<GopayPayment> getPaymentsToday() {
        LocalDate startOfDay = LocalDate.now();
        LocalDate now = LocalDate.now();
        return repository.findByTransactionDateBetween(startOfDay, now);
    }

    // Mendapatkan pembayaran untuk "7 Hari Terakhir"
    public List<GopayPayment> getPaymentsLast7Days() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        LocalDate now = LocalDate.now();
        return repository.findByTransactionDateBetween(sevenDaysAgo, now);
    }

    // Mendapatkan pembayaran untuk "30 Hari Terakhir"
    public List<GopayPayment> getPaymentsLast30Days() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate now = LocalDate.now();
        return repository.findByTransactionDateBetween(thirtyDaysAgo, now);
    }

    // Mendapatkan pembayaran untuk "Bulan Ini"
    public List<GopayPayment> getPaymentsThisMonth() {
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate now = LocalDate.now();
        return repository.findByTransactionDateBetween(startOfMonth, now);
    }
}
