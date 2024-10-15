package com.project.midtrans2.transactionvolume.repository;

import com.project.midtrans2.transactionvolume.model.GopayPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface GopayPaymentRepository extends JpaRepository<GopayPayment, Long> {

    // Query untuk mendapatkan transaksi setelah tanggal tertentu (Today, Last 7 Days, Last 30 Days)
    @Query("SELECT g FROM GopayPayment g WHERE g.transactionDate >= :startDate")
    List<GopayPayment> findByTransactionDateAfter(@Param("startDate") LocalDateTime startDate);

    // Query untuk mendapatkan transaksi di antara dua tanggal (This Month atau rentang waktu tertentu)
    @Query("SELECT g FROM GopayPayment g WHERE g.transactionDate >= :startDate AND g.transactionDate < :endDate")
    List<GopayPayment> findByTransactionDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

