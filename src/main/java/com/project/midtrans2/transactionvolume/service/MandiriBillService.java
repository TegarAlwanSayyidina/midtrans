package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.MandiriBill;
import com.project.midtrans2.transactionvolume.repository.MandiriBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<MandiriBill> getPaymentsForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsToday() {
        LocalDateTime startDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<MandiriBill> getPaymentsThisMonth() {
        LocalDateTime startDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }
}

