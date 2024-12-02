package com.project.midtrans2.transactionvolume.controller;

import com.project.midtrans2.transactionvolume.model.MandiriBill;
import com.project.midtrans2.transactionvolume.service.MandiriBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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
        LocalDate today = LocalDate.now();
        return service.getPaymentsForPeriod(today, today);
    }

    // Endpoint untuk filter berdasarkan "7 Hari Terakhir"
    @GetMapping("/last-7-days")
    public List<MandiriBill> getLast7Days() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        LocalDate today = LocalDate.now();
        return service.getPaymentsForPeriod(sevenDaysAgo, today);
    }

    // Endpoint untuk filter berdasarkan "30 Hari Terakhir"
    @GetMapping("/last-30-days")
    public List<MandiriBill> getLast30Days() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        LocalDate today = LocalDate.now();
        return service.getPaymentsForPeriod(thirtyDaysAgo, today);
    }

    // Endpoint untuk filter berdasarkan "Bulan Ini"
    @GetMapping("/this-month")
    public List<MandiriBill> getThisMonth() {
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth()).minusDays(1);
        return service.getPaymentsForPeriod(startOfMonth, endOfMonth);
    }
}
