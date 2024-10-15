package com.project.midtrans2.transactionvolume.repository;

import com.project.midtrans2.transactionvolume.model.BankTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BankTransferRepository extends JpaRepository<BankTransfer, Long> {

    // Query untuk mendapatkan transaksi setelah tanggal tertentu (Today, Last 7 Days, Last 30 Days)
    @Query("SELECT b FROM BankTransfer b WHERE b.transactionDate >= :startDate")
    List<BankTransfer> findByTransactionDateAfter(@Param("startDate") LocalDateTime startDate);

    // Query untuk mendapatkan transaksi di antara dua tanggal (This Month atau rentang waktu tertentu)
    @Query("SELECT b FROM BankTransfer b WHERE b.transactionDate >= :startDate AND b.transactionDate < :endDate")
    List<BankTransfer> findByTransactionDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

