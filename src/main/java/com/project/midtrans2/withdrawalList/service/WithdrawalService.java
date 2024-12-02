package com.project.midtrans2.withdrawalList.service;

import com.project.midtrans2.withdrawalList.model.Withdrawal;
import com.project.midtrans2.withdrawalList.repository.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WithdrawalService {

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    public List<Withdrawal> getWithdrawalsByFilters(String orderId, String startDate, String endDate, String status, String quickFilter) {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        switch (quickFilter) {
            case "Today":
                start = LocalDate.now().atStartOfDay();
                break;
            case "Yesterday":
                start = LocalDate.now().minusDays(1).atStartOfDay();
                end = LocalDate.now().atStartOfDay();
                break;
            case "Last 7 days":
                start = LocalDate.now().minusDays(7).atStartOfDay();
                break;
            case "Last month":
                start = LocalDate.now().minusMonths(1).atStartOfDay();
                break;
        }

        if (startDate != null && endDate != null) {
            start = LocalDateTime.parse(startDate);
            end = LocalDateTime.parse(endDate);
        }

        if (orderId != null && status != null) {
            return withdrawalRepository.findByOrderIdContainingAndDateTimeBetweenAndStatus(orderId, start, end, status);
        } else if (orderId != null) {
            return withdrawalRepository.findByOrderIdContaining(orderId);
        } else if (status != null) {
            return withdrawalRepository.findByStatus(status);
        } else {
            return withdrawalRepository.findByDateRange(start, end);
        }
    }
}

