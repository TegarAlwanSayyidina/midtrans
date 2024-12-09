package com.project.midtrans2.summary.totalpayableamount.service;

import com.project.midtrans2.summary.totalpayableamount.model.PayableAmount;
import com.project.midtrans2.summary.totalpayableamount.repository.PayableAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PayableAmountService {

    @Autowired
    private PayableAmountRepository payableAmountRepository;

    public PayableAmount savePayableAmount(PayableAmount payableAmount) {
        return payableAmountRepository.save(payableAmount);
    }

    public BigDecimal getTotalPayableAmountMonthToDate() {
        List<PayableAmount> amounts = payableAmountRepository.findAll();
        return amounts.stream()
                .filter(a -> a.getTransactionDate().getMonth().equals(LocalDate.now().getMonth()))
                .map(PayableAmount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
