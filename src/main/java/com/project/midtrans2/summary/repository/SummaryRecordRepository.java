package com.project.midtrans2.summary.repository;

import com.project.midtrans2.summary.model.SummaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface SummaryRecordRepository extends JpaRepository<SummaryRecord, Long> {

    @Query("SELECT SUM(s.amount) FROM SummaryRecord s WHERE s.recordDate >= :startOfMonth")
    BigDecimal getTotalVolumeMonthToDate(LocalDateTime startOfMonth);

    @Query("SELECT COUNT(s) FROM SummaryRecord s WHERE s.recordDate >= :startOfMonth")
    Long getTotalTransactionMonthToDate(LocalDateTime startOfMonth);

    @Query("SELECT SUM(s.amount) FROM SummaryRecord s WHERE s.status = 'PAYABLE' AND s.recordDate >= :startOfMonth")
    BigDecimal getPayableAmountMonthToDate(LocalDateTime startOfMonth);
}

