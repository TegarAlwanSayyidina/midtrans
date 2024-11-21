package com.project.midtrans2.paymentlink.repository;


import com.project.midtrans2.paymentlink.model.TransactionLink;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TransactionPaymentLinkRepository extends JpaRepository<TransactionLink, Long> {
    Optional<TransactionLink> findByOrderId(String orderId);
}