package com.project.midtrans2.transactionlist.controller;

import com.project.midtrans2.transactionlist.model.Transaction;
import com.project.midtrans2.transactionlist.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTransactions(
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) LocalDateTime dateTime,
            @RequestParam(required = false) String quickFilter,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Double amount,
            @RequestParam(required = false) String customerEmail) {

        List<Transaction> transactions = null;

        // Filter berdasarkan Order ID
        if (orderId != null && !orderId.isEmpty()) {
            transactions = transactionService.getTransactionsByOrderId(orderId);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Order ID: " + orderId);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan Channel
        if (channel != null && !channel.isEmpty()) {
            transactions = transactionService.getTransactionsByChannel(channel);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Channel: " + channel);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan Transaction Type
        if (transactionType != null && !transactionType.isEmpty() && !"All".equalsIgnoreCase(transactionType)) {
            transactions = transactionService.getTransactionsByType(transactionType);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Transaction Type: " + transactionType);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan Status
        if (status != null && !status.isEmpty()) {
            transactions = transactionService.getTransactionsByStatus(status);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Status: " + status);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan Amount
        if (amount != null) {
            transactions = transactionService.getTransactionsByAmount(amount);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Amount: " + amount);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan Customer Email
        if (customerEmail != null && !customerEmail.isEmpty()) {
            transactions = transactionService.getTransactionsByCustomerEmail(customerEmail);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Customer Email: " + customerEmail);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan DateTime
        if (dateTime != null) {
            transactions = transactionService.getTransactionsByDateTime(dateTime);
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for DateTime: " + dateTime);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan Quick Filter
        if (quickFilter != null && !quickFilter.isEmpty()) {
            LocalDateTime now = LocalDateTime.now();
            switch (quickFilter.toLowerCase()) {
                case "today":
                    transactions = transactionService.getTransactionsToday();
                    break;
                case "yesterday":
                    transactions = transactionService.getTransactionsYesterday();
                    break;
                case "last7days":
                    transactions = transactionService.getTransactionsLast7Days();
                    break;
                case "last30days":
                    transactions = transactionService.getTransactionsLast30Days();
                    break;
                case "thismonth":
                    transactions = transactionService.getTransactionsThisMonth();
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid quick filter: " + quickFilter);
            }
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for Quick Filter: " + quickFilter);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Filter berdasarkan startDate dan endDate
        if (startDate != null && endDate != null) {
            transactions = transactionService.getTransactionsByDateRange(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
            if (transactions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No transactions found for the given date range.");
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }

        // Default: Kembalikan semua transaksi jika tidak ada filter yang diberikan
        transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No transactions found.");
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }


    @GetMapping("/reset")
    public ResponseEntity<List<Transaction>> resetFilters() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
