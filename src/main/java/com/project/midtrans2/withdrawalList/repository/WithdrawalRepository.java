package com.project.midtrans2.withdrawalList.repository;

import com.project.midtrans2.withdrawalList.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Withdrawal> findByOrderId(String orderId);
    List<Withdrawal> findByStatus(Withdrawal.Status status);
}