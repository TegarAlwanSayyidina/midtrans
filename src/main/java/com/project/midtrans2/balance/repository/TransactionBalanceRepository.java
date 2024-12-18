package com.project.midtrans2.balance.repository;

import com.project.midtrans2.balance.model.TransactionBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionBalanceRepository extends JpaRepository<TransactionBalance, Long> {

    Optional<TransactionBalance> findByOrderId(String orderId);

    // Query untuk menghitung total saldo yang dapat ditarik
    @Query("SELECT SUM(t.grossAmount - t.fee) FROM TransactionBalance t WHERE "
            + "t.status = 'Settlement'")
    Double calculateWithdrawableBalance();

    // Query untuk menghitung total saldo yang dapat ditarik berdasarkan rentang tanggal
    @Query("SELECT SUM(t.grossAmount - t.fee) FROM TransactionBalance t WHERE "
            + "t.status = 'Settlement' AND "
            + "t.createdDate BETWEEN :startDate AND :endDate")
    Double calculateWithdrawableBalanceWithinDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Menyaring transaksi berdasarkan rentang tanggal
    List<TransactionBalance> findAllByCreatedDateBetween(LocalDate startDate, LocalDate endDate);

    // Filter transaksi berdasarkan parameter yang diberikan
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
