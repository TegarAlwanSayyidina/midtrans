package com.project.midtrans2.transactionvolume.controller;


import com.project.midtrans2.transactionvolume.model.MandiriBill;
import com.project.midtrans2.transactionvolume.service.MandiriBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mandiri-bill")
public class MandiriBillController {

    @Autowired
    private MandiriBillService service;

    @PostMapping
    public MandiriBill create(@RequestBody MandiriBill mandiriBill) {
        return service.save(mandiriBill);
    }

    @GetMapping
    public List<MandiriBill> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public MandiriBill getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    // Endpoint untuk filter berdasarkan "Hari Ini"
    @GetMapping("/today")
    public List<MandiriBill> getToday() {
        return service.getPaymentsForPeriod(
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay()
        );
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<MandiriBill> getLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<MandiriBill> getLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }

    // Endpoint untuk filter berdasarkan "Bulan Ini"
    @GetMapping("/this-month")
    public List<MandiriBill> getThisMonth() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        return service.getPaymentsForPeriod(startDate, endDate);
    }
}

