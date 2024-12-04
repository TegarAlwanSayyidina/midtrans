package com.project.midtrans2.cardacceptancerate.Controller;

import com.project.midtrans2.cardacceptancerate.Model.PaymentTypeCardAcceptanceRate;
import com.project.midtrans2.cardacceptancerate.Service.PaymentTypeCardAcceptanceRateService;
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

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/payment-type")
public class PaymentTypeCardAcceptanceRateController {

    @Autowired
    private PaymentTypeCardAcceptanceRateService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPaymentTypes() {
        List<PaymentTypeCardAcceptanceRate> data = service.getAllPaymentType();
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/between-dates")
    public ResponseEntity<Map<String, Object>> getPaymentTypeBetweenDates(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<PaymentTypeCardAcceptanceRate> data = service.getPaymentTypeBetweenDates(startDate, endDate);
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addPaymentType(@RequestBody PaymentTypeCardAcceptanceRate paymentType) {
        try {
            PaymentTypeCardAcceptanceRate savedData = service.savePaymentType(paymentType);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Payment Type added successfully");
            response.put("data", Map.of(
                    "id", savedData.getId(),
                    "date", savedData.getDate().toString(),
                    "type", savedData.getType()
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
                    "path", "/api/payment-type"
            ));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getPaymentTypeStats(@RequestParam String range) {
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
                    "path", "/api/payment-type/statistics"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "timestamp", LocalDateTime.now().toString(),
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "An unexpected error occurred",
                    "path", "/api/payment-type/statistics"
            ));
        }
    }
}