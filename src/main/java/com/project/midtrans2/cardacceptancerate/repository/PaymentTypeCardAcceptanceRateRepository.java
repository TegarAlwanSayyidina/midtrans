package com.project.midtrans2.cardacceptancerate.repository;

import com.project.midtrans2.cardacceptancerate.Model.PaymentTypeCardAcceptanceRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentTypeCardAcceptanceRateRepository extends JpaRepository<PaymentTypeCardAcceptanceRate, Long> {
    List<PaymentTypeCardAcceptanceRate> findByDateBetween(LocalDate startDate, LocalDate endDate);
}