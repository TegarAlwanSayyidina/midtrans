package com.project.midtrans2.withdrawalList.repository;

import com.project.midtrans2.withdrawalList.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    List<Withdrawal> findByOrderIdContaining(String orderId);

    @Query("SELECT w FROM Withdrawal w WHERE w.dateTime BETWEEN :startDate AND :endDate")
    List<Withdrawal> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<Withdrawal> findByStatus(String status);

    List<Withdrawal> findByOrderIdContainingAndDateTimeBetweenAndStatus(String orderId, LocalDateTime startDate, LocalDateTime endDate, String status);
}
