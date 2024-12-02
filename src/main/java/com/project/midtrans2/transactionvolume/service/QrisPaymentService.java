package com.project.midtrans2.transactionvolume.service;

import com.project.midtrans2.transactionvolume.model.QrisPayment;
import com.project.midtrans2.transactionvolume.repository.QrisPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<QrisPayment> getPaymentsForPeriod(LocalDate startDate, LocalDate endDate) {
        return repository.findByTransactionDateBetween(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForToday() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForLast7Days() {
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForLast30Days() {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(1);
        return getPaymentsForPeriod(startDate, endDate);
    }

    public List<QrisPayment> getPaymentsForLastMonth() {
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(1);
        return getPaymentsForPeriod(startDate, endDate);
    }
}
