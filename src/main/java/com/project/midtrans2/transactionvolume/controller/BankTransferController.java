package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.BankTransfer;
import com.project.midtrans2.transactionvolume.service.BankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/banktransfers")
public class BankTransferController {

    @Autowired
    private BankTransferService bankTransferService;

    @PostMapping
    public BankTransfer createTransfer(@RequestBody BankTransfer bankTransfer) {
        return bankTransferService.saveTransfer(bankTransfer);
    }

    @GetMapping
    public List<BankTransfer> getAllTransfers() {
        return bankTransferService.getAllTransfers();
    }

    @GetMapping("/{id}")
    public BankTransfer getTransferById(@PathVariable Long id) {
        return bankTransferService.getTransferById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTransfer(@PathVariable Long id) {
        bankTransferService.deleteTransfer(id);
    }

    // Endpoint untuk filter berdasarkan "Hari Ini"
    @GetMapping("/today")
    public List<BankTransfer> getTransfersToday() {
        return bankTransferService.getTransfersForPeriod(
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay()
        );
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<BankTransfer> getTransfersLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return bankTransferService.getTransfersForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<BankTransfer> getTransfersLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return bankTransferService.getTransfersForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Ini"
    @GetMapping("/this-month")
    public List<BankTransfer> getTransfersThisMonth() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        return bankTransferService.getTransfersForPeriod(startDate, endDate);
    }
}

