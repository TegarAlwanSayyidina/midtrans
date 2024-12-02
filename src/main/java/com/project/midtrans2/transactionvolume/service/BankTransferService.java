package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.BankTransfer;
import com.project.midtrans2.transactionvolume.repository.BankTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<BankTransfer> getTransfersForPeriod(LocalDate startDate, LocalDate endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    // Mendapatkan transaksi hari ini
    public List<BankTransfer> getTransfersForToday() {
        LocalDate today = LocalDate.now();
        return getTransfersForPeriod(today, today.plusDays(1));
    }

    // Mendapatkan transaksi 7 hari terakhir
    public List<BankTransfer> getTransfersForLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return getTransfersForPeriod(startDate, endDate);
    }

    // Mendapatkan transaksi 30 hari terakhir
    public List<BankTransfer> getTransfersForLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return getTransfersForPeriod(startDate, endDate);
    }

    // Mendapatkan transaksi bulan ini
    public List<BankTransfer> getTransfersForThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return getTransfersForPeriod(startDate, endDate);
    }
}
