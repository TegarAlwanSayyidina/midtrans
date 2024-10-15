package com.project.midtrans2.transactionvolume.repository;

import com.project.midtrans2.transactionvolume.model.QrisPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QrisPaymentRepository extends JpaRepository<QrisPayment, Long> {

    List<QrisPayment> findByTransactionDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}


