package com.project.midtrans2.summary.totaltransaction.controller;

import com.project.midtrans2.summary.totaltransaction.model.TotalTransactionResponse;
import com.project.midtrans2.summary.totaltransaction.model.TotalTransaction;
import com.project.midtrans2.summary.totaltransaction.service.TotalTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/total-transaction")
@CrossOrigin("http://localhost:4200")
public class TotalTransactionController {

    @Autowired
    private TotalTransactionService totalTransactionService;

    @PostMapping("/add")
    public ResponseEntity<TotalTransaction> addTotalTransaction(@RequestBody TotalTransaction totalTransaction) {
        TotalTransaction savedTransaction = totalTransactionService.saveTotalTransaction(totalTransaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @GetMapping("/month-to-date")
    public ResponseEntity<TotalTransactionResponse> getTotalTransactionMonthToDate() {
        TotalTransactionResponse response = totalTransactionService.getTotalTransactionMonthToDate();
        return ResponseEntity.ok(response);
    }
}
