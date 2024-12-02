package com.project.midtrans2.paymentreminder.service;

import com.project.midtrans2.paymentreminder.model.PaymentReminder;
import com.project.midtrans2.paymentreminder.repository.PaymentReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentReminderService {

    private final PaymentReminderRepository paymentReminderRepository;

    @Autowired
    public PaymentReminderService(PaymentReminderRepository paymentReminderRepository) {
        this.paymentReminderRepository = paymentReminderRepository;
    }

    public PaymentReminder createPaymentReminder(PaymentReminder paymentReminder) {
        // Simpan data payment reminder ke database
        return paymentReminderRepository.save(paymentReminder);
    }

    public Optional<PaymentReminder> getPaymentReminderById(Long id) {
        return paymentReminderRepository.findById(id);
    }

    public List<PaymentReminder> getAllPaymentReminders() {
        // Mengambil semua data payment reminder dari database
        return paymentReminderRepository.findAll();
    }
}
