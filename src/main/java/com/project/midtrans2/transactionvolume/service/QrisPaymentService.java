package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.QrisPayment;
import com.project.midtrans2.transactionvolume.repository.QrisPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QrisPaymentService {

    @Autowired
    private QrisPaymentRepository repository;

    public QrisPayment createPayment(QrisPayment payment) {
        return repository.save(payment);
    }

    public QrisPayment getPaymentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<QrisPayment> getAllPayments() {
        return repository.findAll();
    }

    public void deletePayment(Long id) {
        repository.deleteById(id);
    }

    public List<QrisPayment> getPaymentsForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForToday() {
        LocalDateTime startDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForLast7Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForLast30Days() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForLastMonth() {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1).withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        return getPaymentsForPeriod(startDate, endDate);
    }
}

