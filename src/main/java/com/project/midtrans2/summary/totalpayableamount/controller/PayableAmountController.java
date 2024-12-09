package com.project.midtrans2.summary.totalpayableamount.controller;

import com.project.midtrans2.summary.totalpayableamount.model.PayableAmount;
import com.project.midtrans2.summary.totalpayableamount.service.PayableAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payable-amount")
@CrossOrigin("http://localhost:4200")
public class PayableAmountController {

    @Autowired
    private PayableAmountService payableAmountService;

    @PostMapping("/add")
    public ResponseEntity<PayableAmount> addPayableAmount(@RequestBody PayableAmount payableAmount) {
        PayableAmount savedPayableAmount = payableAmountService.savePayableAmount(payableAmount);
        return ResponseEntity.ok(savedPayableAmount);
    }

    @GetMapping("/month-to-date")
    public ResponseEntity<Map<String, BigDecimal>> getPayableAmountMonthToDate() {
        BigDecimal payableAmount = payableAmountService.getTotalPayableAmountMonthToDate();
        Map<String, BigDecimal> response = new HashMap<>();
        response.put("amount", payableAmount);
        return ResponseEntity.ok(response);
    }
}
