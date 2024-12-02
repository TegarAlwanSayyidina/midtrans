package com.project.midtrans2.transactionvolume.repository;

import com.project.midtrans2.transactionvolume.model.QrisPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface QrisPaymentRepository extends JpaRepository<QrisPayment, Long> {

    // Menggunakan LocalDate untuk range tanggal
    List<QrisPayment> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);
}
