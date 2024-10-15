package com.project.midtrans2.transactionlist.service;

import com.project.midtrans2.transactionlist.model.Transaction;
import com.project.midtrans2.transactionlist.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Menyimpan transaksi baru
    public Transaction saveTransaction(Transaction transaction) {
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
    public List<Transaction> getTransactionsByDateTime(LocalDateTime dateTime) {
        return transactionRepository.findByDateTime(dateTime);
    }

    // Mendapatkan transaksi berdasarkan rentang tanggal
    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByDateTimeBetween(startDate, endDate);
    }

    // Mendapatkan transaksi yang terjadi hari ini
    public List<Transaction> getTransactionsToday() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        return transactionRepository.findByDateTimeBetween(startOfDay, now);
    }

    // Mendapatkan transaksi yang terjadi kemarin
    public List<Transaction> getTransactionsYesterday() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfYesterday = now.minusDays(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfYesterday = startOfYesterday.plusDays(1).minusNanos(1);
        return transactionRepository.findByDateTimeBetween(startOfYesterday, endOfYesterday);
    }


    // Mendapatkan transaksi yang terjadi dalam 7 hari terakhir
    public List<Transaction> getTransactionsLast7Days() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfPeriod = now.minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return transactionRepository.findByDateTimeBetween(startOfPeriod, now);
    }

    // Mendapatkan transaksi yang terjadi dalam 30 hari terakhir
    public List<Transaction> getTransactionsLast30Days() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfPeriod = now.minusDays(30).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return transactionRepository.findByDateTimeBetween(startOfPeriod, now);
    }
}
