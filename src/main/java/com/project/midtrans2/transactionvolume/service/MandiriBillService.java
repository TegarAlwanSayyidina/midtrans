package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.MandiriBill;
import com.project.midtrans2.transactionvolume.repository.MandiriBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MandiriBillService {

    @Autowired
    private MandiriBillRepository repository;

    public MandiriBill save(MandiriBill mandiriBill) {
        return repository.save(mandiriBill);
    }

    public List<MandiriBill> findAll() {
        return repository.findAll();
    }

    public MandiriBill findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<MandiriBill> getPaymentsForPeriod(LocalDate startDate, LocalDate endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsToday() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return getPaymentsForPeriod(startDate, endDate);
    }
}
