package com.project.midtrans2.paymentreminder.repository;

import com.project.midtrans2.paymentreminder.model.PaymentReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReminderRepository extends JpaRepository<PaymentReminder, Long> {
    // Menambahkan method query sesuai kebutuhan jika diperlukan
}

