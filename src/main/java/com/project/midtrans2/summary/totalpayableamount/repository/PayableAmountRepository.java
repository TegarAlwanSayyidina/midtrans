package com.project.midtrans2.summary.totalpayableamount.repository;

import com.project.midtrans2.summary.totalpayableamount.model.PayableAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayableAmountRepository extends JpaRepository<PayableAmount, Long> {
}
