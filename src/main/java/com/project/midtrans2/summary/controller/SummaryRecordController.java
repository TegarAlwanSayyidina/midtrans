package com.project.midtrans2.summary.controller;


import com.project.midtrans2.summary.service.SummaryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/summary")
public class SummaryRecordController {

    @Autowired
    private SummaryRecordService summaryRecordService;

    @GetMapping("/total-volume-month-to-date")
    public ResponseEntity<BigDecimal> getTotalVolumeMonthToDate() {
        BigDecimal totalVolume = summaryRecordService.getTotalVolumeMonthToDate();
        return ResponseEntity.ok(totalVolume);
    }

    @GetMapping("/total-transaction-month-to-date")
    public ResponseEntity<Long> getTotalTransactionMonthToDate() {
        Long totalTransactions = summaryRecordService.getTotalTransactionMonthToDate();
        return ResponseEntity.ok(totalTransactions);
    }

    @GetMapping("/payable-amount-month-to-date")
    public ResponseEntity<BigDecimal> getPayableAmountMonthToDate() {
        BigDecimal payableAmount = summaryRecordService.getPayableAmountMonthToDate();
        return ResponseEntity.ok(payableAmount);
    }
}

