package com.project.midtrans2.balance.repository;

import com.project.midtrans2.balance.model.TransactionBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionBalanceRepository extends JpaRepository<TransactionBalance, String> {

    Optional<TransactionBalance> findByOrderId(String orderId);

    List<TransactionBalance> findAllByStatus(String status);

    // Menyesuaikan query untuk menggunakan LocalDate (tanpa waktu)
    List<TransactionBalance> findAllByCreatedDateBetween(LocalDate startDate, LocalDate endDate);

    List<TransactionBalance> findAllByPaymentChannel(String paymentChannel);

    // Metode untuk filter berdasarkan TransactionFilterRequest
    @Query("SELECT t FROM TransactionBalance t WHERE "
            + "(:paymentType IS NULL OR t.paymentType = :paymentType) AND "
            + "(:statuses IS NULL OR t.status IN :statuses) AND "
            + "(:paymentChannel IS NULL OR t.paymentChannel = :paymentChannel) AND "
            + "t.createdDate BETWEEN :startDate AND :endDate")
    List<TransactionBalance> filterTransactions(
            @Param("paymentType") String paymentType,
            @Param("statuses") List<String> statuses,
            @Param("paymentChannel") String paymentChannel,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}