package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.BankTransfer;
import com.project.midtrans2.transactionvolume.service.BankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        LocalDate today = LocalDate.now();
        return bankTransferService.getTransfersForPeriod(today, today.plusDays(1));
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<BankTransfer> getTransfersLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return bankTransferService.getTransfersForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<BankTransfer> getTransfersLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return bankTransferService.getTransfersForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Ini"
    @GetMapping("/this-month")
    public List<BankTransfer> getTransfersThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return bankTransferService.getTransfersForPeriod(startDate, endDate);
    }
}
