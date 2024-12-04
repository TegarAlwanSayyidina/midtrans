package com.project.midtrans2.cardacceptancerate.Controller;

import com.project.midtrans2.cardacceptancerate.Model.TopIssuingBank;
import com.project.midtrans2.cardacceptancerate.Service.TopIssuingBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/top-issuing-bank")
public class TopIssuingBankController {

    @Autowired
    private TopIssuingBankService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getTopIssuingBank(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        Map<String, Object> response = new HashMap<>();
        try {
            if (startDate != null && endDate != null) {
                List<TopIssuingBank> data = service.getTopIssuingBankBetweenDates(startDate, endDate);
                response.put("data", data);
            } else {
                List<TopIssuingBank> allData = service.getAllTopIssuingBank();
                response.put("data", allData);
            }
            response.put("timestamp", LocalDateTime.now().toString());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Internal Server Error");
            response.put("message", "An unexpected error occurred.");
            response.put("path", "/api/top-issuing-bank");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addTopIssuingBank(@RequestBody TopIssuingBank topIssuingBank) {
        try {
            if (topIssuingBank.getBankName() == null || topIssuingBank.getBankName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Bank name cannot be null or empty"));
            }
            if (topIssuingBank.getDate() == null) {
                return ResponseEntity.badRequest().body(Map.of("message", "Date cannot be null"));
            }
            if (topIssuingBank.getTransactions() < 0) {
                return ResponseEntity.badRequest().body(Map.of("message", "Transactions cannot be negative"));
            }

            TopIssuingBank savedData = service.saveTopIssuingBank(topIssuingBank);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Top Issuing Bank added successfully");
            response.put("data", Map.of(
                    "id", savedData.getId(),
                    "date", savedData.getDate().toString(),
                    "bankName", savedData.getBankName(),
                    "transactions", savedData.getTransactions()
            ));
            response.put("timestamp", now.format(dateFormatter) + " " + now.format(timeFormatter));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "An unexpected error occurred",
                    "path", "/api/top-issuing-bank"
            ));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getTopIssuingBankStats(@RequestParam String range) {
        try {
            Map<String, Object> stats = service.getStatistics(range);
            stats.put("timestamp", LocalDateTime.now().toString());
            return ResponseEntity.ok(stats);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "error", "Bad Request",
                    "message", e.getMessage(),
                    "path", "/api/top-issuing-bank/statistics"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "An unexpected error occurred",
                    "path", "/api/top-issuing-bank/statistics"
            ));
        }
    }
}
