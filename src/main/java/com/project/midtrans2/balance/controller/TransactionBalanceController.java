package com.project.midtrans2.balance.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.project.midtrans2.balance.dto.TransactionFilterRequest;
import com.project.midtrans2.balance.model.TransactionBalance;
import com.project.midtrans2.balance.service.TransactionBalanceService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/transactions")
public class TransactionBalanceController {

    private final TransactionBalanceService transactionService;
    private static final Logger logger = LoggerFactory.getLogger(TransactionBalanceController.class);

    public TransactionBalanceController(TransactionBalanceService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterTransactions(
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) String paymentChannel,
            @RequestParam(required = false) String transactionId,
            @RequestParam(required = false) Integer fee,
            @RequestParam(required = false) String paymentType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate) {

        LocalDate finalStartDate = startDate.orElse(LocalDate.now().minusMonths(1));
        LocalDate finalEndDate = endDate.orElse(LocalDate.now());

        if (finalStartDate.isAfter(finalEndDate)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Start date cannot be after end date.");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        logger.info("Filtering transactions with startDate: {}, endDate: {}", finalStartDate, finalEndDate);

        TransactionFilterRequest filterRequest = new TransactionFilterRequest();
        filterRequest.setStatus(status);
        filterRequest.setPaymentChannel(paymentChannel);
        filterRequest.setTransactionId(transactionId);
        filterRequest.setFee(fee);
        filterRequest.setPaymentType(paymentType);

        List<TransactionBalance> transactions = transactionService.filterTransactions(filterRequest, finalStartDate, finalEndDate);
        logger.info("Filtered transactions count: {}", transactions.size());

        Map<String, Object> response = new HashMap<>();
        response.put("transactions", transactions);
        response.put("count", transactions.size());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<String> getTransactionStatus(@PathVariable String orderId) {
        String status = transactionService.handleTransactionStatus(orderId);
        logger.info("Transaction status for orderId {}: {}", orderId, status);
        return ResponseEntity.ok("Transaction status updated: " + status);
    }

    @PostMapping("/notification")
    public ResponseEntity<String> handlePaymentNotification(@RequestBody Map<String, Object> notification) {
        String result = transactionService.handlePaymentNotification(notification);
        logger.info("Payment notification handled: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/order/all")
    public ResponseEntity<Map<String, Object>> getAllTransactions() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<TransactionBalance> transactions = transactionService.getAllTransactions();
            response.put("transactions", transactions.isEmpty() ? "No transactions found." : transactions);
            response.put("count", transactions.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching all transactions: ", e);
            response.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/refund/{orderId}")
    public ResponseEntity<String> refundTransaction(@PathVariable String orderId, @RequestParam double amount) {
        String result = transactionService.refundTransaction(orderId, amount);
        logger.info("Refund request for orderId {}: {}", orderId, result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<TransactionBalance> addTransaction(@Valid @RequestBody TransactionBalance transaction) {
        try {
            transaction.setCreatedDate(LocalDate.now());
            TransactionBalance savedTransaction = transactionService.saveTransaction(transaction);
            logger.info("Transaction added with orderId: {}", savedTransaction.getOrderId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
        } catch (Exception e) {
            logger.error("Error adding transaction: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/statuses")
    public ResponseEntity<Map<String, List<String>>> getAllTransactionStatuses() {
        Map<String, List<String>> statuses = Map.of(
                "Payment", List.of("Success", "Gagal", "Pending"),
                "Withdrawal", List.of("Requested", "In progress", "Success", "Failed", "Cancelled"),
                "Balance transfer", List.of("Success"),
                "Fee", List.of("Success")
        );
        logger.info("Fetched all transaction statuses");
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/channels")
    public ResponseEntity<Map<String, List<String>>> getAllTransactionChannels() {
        Map<String, List<String>> channels = Map.of(
                "Payment", List.of(
                        "BSI Bank Transfer", "Indomaret", "BCA Bank Transfer", "BNI Bank Transfer",
                        "BRI Bank Transfer", "LinkAja", "Credit Card", "Permata Bank Transfer",
                        "CIMB Bank Transfer", "ShopeePay", "Mandiri Bill", "Akulaku",
                        "Kredivo", "Alfamart", "GO-PAY"
                ),
                "Withdrawal", List.of(
                        "PT. BANK ACEH", "PT. BPD ISTIMEWA ACEH SYARIAH", "PT. BANK AGRIS",
                        "PT. BANK JAGO TBK.", "PT. BANK SYARIAH INDONESIA, TBK.",
                        "PT. ALLO BANK INDONESIA TBK."
                ),
                "Balance transfer", List.of(
                        "Partner to merchant", "Merchant to partner", "Merchant to merchant"
                ),
                "Fee", List.of("Invoicing fee")
        );
        logger.info("Fetched all transaction channels");
        return ResponseEntity.ok(channels);
    }

    @GetMapping("/orderId/{orderId}")
    public ResponseEntity<Object> getTransactionByOrderId(@PathVariable String orderId) {
        logger.info("Fetching transaction for orderId: {}", orderId);

        if ("all".equalsIgnoreCase(orderId)) {
            // Jika orderId adalah "all", ambil semua transaksi
            List<TransactionBalance> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(transactions); // Mengembalikan list transaksi
        } else {
            // Jika orderId bukan "all", cari transaksi berdasarkan orderId
            Optional<TransactionBalance> transaction = transactionService.getTransactionByOrderId(orderId);
            logger.info("Transaction found: {}", transaction);

            // Jika transaksi ditemukan, mengembalikan transaksi dalam array
            if (transaction.isPresent()) {
                return ResponseEntity.ok(List.of(transaction.get()));  // Membungkus dalam array
            } else {
                // Jika transaksi tidak ditemukan, mengembalikan error
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("orderId", orderId);
                errorResponse.put("error", "Transaction not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        }
    }

}