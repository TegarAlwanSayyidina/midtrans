package com.project.midtrans2.summary.service;

import com.project.midtrans2.summary.repository.SummaryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
public class SummaryRecordService {

    @Autowired
    private SummaryRecordRepository summaryRecordRepository;

    public BigDecimal getTotalVolumeMonthToDate() {
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        return summaryRecordRepository.getTotalVolumeMonthToDate(startOfMonth);
    }

    public Long getTotalTransactionMonthToDate() {
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        return summaryRecordRepository.getTotalTransactionMonthToDate(startOfMonth);
    }

    public BigDecimal getPayableAmountMonthToDate() {
        LocalDateTime startOfMonth = YearMonth.now().atDay(1).atStartOfDay();
        return summaryRecordRepository.getPayableAmountMonthToDate(startOfMonth);
    }
}

