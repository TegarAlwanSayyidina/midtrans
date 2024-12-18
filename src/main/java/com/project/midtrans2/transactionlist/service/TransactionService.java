package com.project.midtrans2.transactionlist.service;

import com.project.midtrans2.transactionlist.model.Transaction;
import com.project.midtrans2.transactionlist.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Mendapatkan semua transaksi
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Menyimpan transaksi baru dengan hanya tanggal, tanpa jam, menit, detik
    public Transaction saveTransaction(Transaction transaction) {
        // Mengatur dateTime hanya menggunakan tahun, bulan, dan tanggal
        transaction.setDateTime(LocalDate.now().atStartOfDay());
        return transactionRepository.save(transaction);
    }

    // Menghapus transaksi berdasarkan ID
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // Mendapatkan transaksi berdasarkan Order ID
    public List<Transaction> getTransactionsByOrderId(String orderId) {
        return transactionRepository.findByOrderId(orderId);
    }

    // Mendapatkan transaksi berdasarkan jenis transaksi
    public List<Transaction> getTransactionsByType(String transactionType) {
        if ("All".equalsIgnoreCase(transactionType)) {
            return transactionRepository.findAll();
        }
        return transactionRepository.findByTransactionType(transactionType);
    }

    // Mendapatkan transaksi berdasarkan status
    public List<Transaction> getTransactionsByStatus(String status) {
        if ("All".equalsIgnoreCase(status)) {
            return transactionRepository.findAll();
        }
        return transactionRepository.findByStatus(status);
    }

    // Mendapatkan transaksi berdasarkan channel
    public List<Transaction> getTransactionsByChannel(String channel) {
        return transactionRepository.findByChannel(channel);
    }

    // Mendapatkan transaksi berdasarkan jenis transaksi dan channel
    public List<Transaction> getTransactionsByTypeAndChannel(String transactionType, String channel) {
        return transactionRepository.findByTransactionTypeAndChannel(transactionType, channel);
    }

    // Mendapatkan transaksi berdasarkan amount
    public List<Transaction> getTransactionsByAmount(Double amount) {
        if (amount != null) {
            return transactionRepository.findByAmount(amount);
        }
        return List.of(); // Kembalikan list kosong jika amount null
    }

    // Mendapatkan transaksi berdasarkan customerEmail
    public List<Transaction> getTransactionsByCustomerEmail(String customerEmail) {
        if (customerEmail != null && !customerEmail.isEmpty()) {
            return transactionRepository.findByCustomerEmail(customerEmail);
        }
        return List.of(); // Kembalikan list kosong jika email null atau kosong
    }

    // Mendapatkan transaksi berdasarkan rentang waktu
    public List<Transaction> getTransactionsByDateTime(LocalDateTime dateTime) {
        return transactionRepository.findByDateTime(dateTime);
    }

    // Mendapatkan transaksi berdasarkan rentang tanggal
    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByDateTimeBetween(startDate, endDate);
    }

    // Mendapatkan transaksi yang terjadi hari ini
    public List<Transaction> getTransactionsToday() {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfDay = now.atStartOfDay();
        return transactionRepository.findByDateTimeBetween(startOfDay, now.atTime(23, 59, 59));
    }

    // Mendapatkan transaksi yang terjadi kemarin
    public List<Transaction> getTransactionsYesterday() {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        return transactionRepository.findByDateTimeBetween(yesterday.atStartOfDay(), yesterday.atTime(23, 59, 59));
    }

    // Mendapatkan transaksi yang terjadi dalam 7 hari terakhir
    public List<Transaction> getTransactionsLast7Days() {
        LocalDate now = LocalDate.now();
        LocalDate startOfPeriod = now.minusDays(7);
        return transactionRepository.findByDateTimeBetween(startOfPeriod.atStartOfDay(), now.atTime(23, 59, 59));
    }

    // Mendapatkan transaksi yang terjadi dalam 30 hari terakhir
    public List<Transaction> getTransactionsLast30Days() {
        LocalDate now = LocalDate.now();
        LocalDate startOfPeriod = now.minusDays(30);
        return transactionRepository.findByDateTimeBetween(startOfPeriod.atStartOfDay(), now.atTime(23, 59, 59));
    }

    // Mendapatkan transaksi yang terjadi dalam bulan ini
    public List<Transaction> getTransactionsThisMonth() {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        return transactionRepository.findByDateTimeBetween(startOfMonth.atStartOfDay(), now.atTime(23, 59, 59));
    }

    // Logika untuk filter dengan multiple kondisi
    public List<Transaction> filterTransactions(String transactionType, String channel, String status, Double amount, String customerEmail) {
        if (transactionType != null && channel != null && status != null) {
            return transactionRepository.findByTransactionTypeAndChannel(transactionType, channel);
        }

        if (transactionType != null) {
            return getTransactionsByType(transactionType);
        }

        if (channel != null) {
            return getTransactionsByChannel(channel);
        }

        if (status != null) {
            return getTransactionsByStatus(status);
        }

        if (amount != null) {
            return getTransactionsByAmount(amount);
        }

        if (customerEmail != null) {
            return getTransactionsByCustomerEmail(customerEmail);
        }

        return List.of(); // Kembalikan list kosong jika tidak ada filter yang diberikan
    }
}