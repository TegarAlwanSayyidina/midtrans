package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.BankTransfer;
import com.project.midtrans2.transactionvolume.repository.BankTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BankTransferService {

    @Autowired
    private BankTransferRepository repository;

    // Menyimpan transaksi baru atau memperbarui transaksi yang ada
    public BankTransfer saveTransfer(BankTransfer bankTransfer) {
        return repository.save(bankTransfer);
    }

    // Mengambil semua transaksi
    public List<BankTransfer> getAllTransfers() {
        return repository.findAll();
    }

    // Mengambil transaksi berdasarkan ID
    public BankTransfer getTransferById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Menghapus transaksi berdasarkan ID
    public void deleteTransfer(Long id) {
        repository.deleteById(id);
    }

    // Mendapatkan transaksi berdasarkan periode
    public List<BankTransfer> getTransfersForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    // Mendapatkan transaksi hari ini
    public List<BankTransfer> getTransfersForToday() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getTransfersForPeriod(startOfDay, endOfDay);
    }

    // Mendapatkan transaksi 7 hari terakhir
    public List<BankTransfer> getTransfersForLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getTransfersForPeriod(startDate, endDate);
    }

    // Mendapatkan transaksi 30 hari terakhir
    public List<BankTransfer> getTransfersForLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getTransfersForPeriod(startDate, endDate);
    }

    // Mendapatkan transaksi bulan ini
    public List<BankTransfer> getTransfersForThisMonth() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        return getTransfersForPeriod(startDate, endDate);
    }
}
