package com.project.midtrans2.transaciton.repository;

import com.project.midtrans2.transaciton.model.PaymentLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentLinkRepository extends JpaRepository<PaymentLink, Long> {
    List<PaymentLink> findByOrderId(String orderId);
}

