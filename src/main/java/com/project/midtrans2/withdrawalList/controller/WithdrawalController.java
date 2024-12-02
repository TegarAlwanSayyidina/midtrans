package com.project.midtrans2.withdrawalList.controller;

import com.project.midtrans2.withdrawalList.model.Withdrawal;
import com.project.midtrans2.withdrawalList.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawals")
public class WithdrawalController {

    @Autowired
    private WithdrawalService withdrawalService;

    @GetMapping("/search")
    public List<Withdrawal> getWithdrawals(@RequestParam(required = false) String orderId,
                                           @RequestParam(required = false) String startDate,
                                           @RequestParam(required = false) String endDate,
                                           @RequestParam(required = false) String status,
                                           @RequestParam(required = false) String quickFilter) {
        return withdrawalService.getWithdrawalsByFilters(orderId, startDate, endDate, status, quickFilter);
    }
}

