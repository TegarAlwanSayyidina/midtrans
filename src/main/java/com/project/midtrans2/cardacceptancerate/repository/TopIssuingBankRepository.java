package com.project.midtrans2.cardacceptancerate.repository;

import com.project.midtrans2.cardacceptancerate.Model.TopIssuingBank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TopIssuingBankRepository extends JpaRepository<TopIssuingBank, Long> {
    List<TopIssuingBank> findByDateBetween(LocalDate startDate, LocalDate endDate);
}