package com.project.midtrans2.summary.totaltransaction.repository;

import com.project.midtrans2.summary.totaltransaction.model.TotalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalTransactionRepository extends JpaRepository<TotalTransaction, Long> {
}
