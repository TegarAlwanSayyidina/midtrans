package com.project.midtrans2.withdrawalList.controller;

import com.project.midtrans2.withdrawalList.model.Withdrawal;
import com.project.midtrans2.withdrawalList.service.WithdrawalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    // Mendapatkan semua withdrawals
    @GetMapping
    public ResponseEntity<List<Withdrawal>> getAllWithdrawals() {
        return ResponseEntity.ok(withdrawalService.getAllWithdrawals());
    }

    // Mendapatkan withdrawal berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Withdrawal> getWithdrawalById(@PathVariable Long id) {
        return ResponseEntity.ok(withdrawalService.getWithdrawalById(id));
    }

    // Filter berdasarkan status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Withdrawal>> getWithdrawalsByStatus(@PathVariable Withdrawal.Status status) {
        return ResponseEntity.ok(withdrawalService.getWithdrawalsByStatus(status));
    }

    // Filter berdasarkan Order ID
    @GetMapping("/order-id/{orderId}")
    public ResponseEntity<List<Withdrawal>> filterByOrderId(@PathVariable String orderId) {
        return ResponseEntity.ok(withdrawalService.filterByOrderId(orderId));
    }

    // Filter berdasarkan rentang tanggal (Start Date & End Date)
    @GetMapping("/date-range")
    public ResponseEntity<List<Withdrawal>> filterByDateRange(@RequestParam("startDate") String startDate,
                                                              @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return ResponseEntity.ok(withdrawalService.filterByDateRange(start, end));
    }

    // Quick filter: Today
    @GetMapping("/quickfilter/today")
    public ResponseEntity<List<Withdrawal>> filterToday() {
        return ResponseEntity.ok(withdrawalService.filterToday());
    }

    // Quick filter: Yesterday
    @GetMapping("/quickfilter/yesterday")
    public ResponseEntity<List<Withdrawal>> filterYesterday() {
        return ResponseEntity.ok(withdrawalService.filterYesterday());
    }

    // Quick filter: Last 7 Days
    @GetMapping("/quickfilter/last7days")
    public ResponseEntity<List<Withdrawal>> filterLast7Days() {
        return ResponseEntity.ok(withdrawalService.filterLast7Days());
    }

    // Quick filter: Last Month
    @GetMapping("/quickfilter/lastmonth")
    public ResponseEntity<List<Withdrawal>> filterLastMonth() {
        return ResponseEntity.ok(withdrawalService.filterLastMonth());
    }

    // Membuat Withdrawal baru
    @PostMapping
    public ResponseEntity<Withdrawal> createWithdrawal(@RequestBody Withdrawal withdrawal) {
        // Validasi status yang diterima
        if (withdrawal.getStatus() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(withdrawalService.createWithdrawal(withdrawal));
    }

    // Update Withdrawal
    @PutMapping("/{id}")
    public ResponseEntity<Withdrawal> updateWithdrawal(@PathVariable Long id, @RequestBody Withdrawal withdrawal) {
        return ResponseEntity.ok(withdrawalService.updateWithdrawal(id, withdrawal));
    }

    // Menghapus Withdrawal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWithdrawal(@PathVariable Long id) {
        withdrawalService.deleteWithdrawal(id);
        return ResponseEntity.noContent().build();
    }
}