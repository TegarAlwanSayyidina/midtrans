package com.project.midtrans2.transactionlist.repository;

import com.project.midtrans2.transactionlist.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOrderId(String orderId);
    List<Transaction> findByTransactionType(String transactionType);
    List<Transaction> findByStatus(String status);
    List<Transaction> findByChannel(String channel);
    List<Transaction> findByTransactionTypeAndChannel(String transactionType, String channel);
    List<Transaction> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByDateTime(LocalDateTime dateTime);
}
