package com.project.midtrans2.summary.totaltransaction.service;

import com.project.midtrans2.summary.totaltransaction.model.TotalTransaction;
import com.project.midtrans2.summary.totaltransaction.repository.TotalTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TotalTransactionService {

    @Autowired
    private TotalTransactionRepository totalTransactionRepository;

    public TotalTransaction saveTotalTransaction(TotalTransaction totalTransaction) {
        return totalTransactionRepository.save(totalTransaction);
    }

    public Long getTotalTransactionMonthToDate() {
        return totalTransactionRepository.findAll().stream()
                .filter(t -> t.getTransactionDate().getMonth().equals(LocalDate.now().getMonth()))
                .count();
    }
}
