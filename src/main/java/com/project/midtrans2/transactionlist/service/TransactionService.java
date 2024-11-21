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
        List<Transaction> result = transactionRepository.findByOrderId(orderId);
        if (result.isEmpty()) {
            System.out.println("No transactions found for Order ID: " + orderId);
        } else {
            System.out.println("Found Transactions for Order ID: " + orderId);
        }
        return result;
    }

    // Mendapatkan transaksi berdasarkan ID
    public Optional<Transaction> getTransactionById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            System.out.println("Found Transaction for ID: " + id);
        } else {
            System.out.println("No transaction found for ID: " + id);
        }
        return transaction;
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
        if ("Payment".equalsIgnoreCase(transactionType) || "Withdrawal".equalsIgnoreCase(transactionType)) {
            return transactionRepository.findByTransactionTypeAndChannel(transactionType, channel);
        }
        return getTransactionsByType(transactionType); // Default behavior jika bukan Payment atau Withdrawal
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
}
