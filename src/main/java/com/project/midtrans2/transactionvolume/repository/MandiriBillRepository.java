package com.project.midtrans2.transactionvolume.repository;

import com.project.midtrans2.transactionvolume.model.MandiriBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MandiriBillRepository extends JpaRepository<MandiriBill, Long> {

    // Query untuk mendapatkan transaksi setelah tanggal tertentu (Today, Last 7 Days, Last 30 Days)
    @Query("SELECT m FROM MandiriBill m WHERE m.transactionDate >= :startDate")
    List<MandiriBill> findByTransactionDateAfter(@Param("startDate") LocalDateTime startDate);

    // Query untuk mendapatkan transaksi di antara dua tanggal (This Month atau rentang waktu tertentu)
    @Query("SELECT m FROM MandiriBill m WHERE m.transactionDate >= :startDate AND m.transactionDate < :endDate")
    List<MandiriBill> findByTransactionDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

