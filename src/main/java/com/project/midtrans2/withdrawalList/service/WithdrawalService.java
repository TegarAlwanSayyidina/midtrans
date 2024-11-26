package com.project.midtrans2.withdrawalList.service;

import com.project.midtrans2.withdrawalList.model.Withdrawal;
import com.project.midtrans2.withdrawalList.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;

    public WithdrawalService(WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    // Mendapatkan semua withdrawals
    public List<Withdrawal> getAllWithdrawals() {
        return withdrawalRepository.findAll();
    }

    // Mendapatkan withdrawal berdasarkan ID
    public Withdrawal getWithdrawalById(Long id) {
        return withdrawalRepository.findById(id).orElseThrow(() -> new RuntimeException("Withdrawal not found with ID: " + id));
    }

    // Filter berdasarkan status
    public List<Withdrawal> getWithdrawalsByStatus(Withdrawal.Status status) {
        return withdrawalRepository.findByStatus(status);
    }

    // Filter berdasarkan Order ID
    public List<Withdrawal> filterByOrderId(String orderId) {
        return withdrawalRepository.findByOrderId(orderId);
    }

    // Filter berdasarkan rentang tanggal
    public List<Withdrawal> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN); // Start of the day
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.MAX); // End of the day
        return withdrawalRepository.findByDateTimeBetween(startDateTime, endDateTime);
    }

    // Filter untuk Today
    public List<Withdrawal> filterToday() {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN); // Start of today (00:00)
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX); // End of today (23:59)
        return withdrawalRepository.findByDateTimeBetween(startOfDay, endOfDay);
    }

    // Filter untuk Yesterday
    public List<Withdrawal> filterYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDateTime startOfDay = LocalDateTime.of(yesterday, LocalTime.MIN); // Start of yesterday (00:00)
        LocalDateTime endOfDay = LocalDateTime.of(yesterday, LocalTime.MAX); // End of yesterday (23:59)
        return withdrawalRepository.findByDateTimeBetween(startOfDay, endOfDay);
    }

    // Filter untuk Last 7 Days
    public List<Withdrawal> filterLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7).toLocalDate().atStartOfDay(); // Start of 7 days ago
        LocalDateTime now = LocalDateTime.now(); // Current time
        return withdrawalRepository.findByDateTimeBetween(sevenDaysAgo, now);
    }

    // Filter untuk Last Month
    public List<Withdrawal> filterLastMonth() {
        LocalDateTime firstDayOfLastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay(); // Start of last month
        LocalDateTime lastDayOfLastMonth = LocalDateTime.of(LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()), LocalTime.MAX); // End of last month (23:59)
        return withdrawalRepository.findByDateTimeBetween(firstDayOfLastMonth, lastDayOfLastMonth);
    }

    // Membuat Withdrawal baru
    public Withdrawal createWithdrawal(Withdrawal withdrawal) {
        return withdrawalRepository.save(withdrawal);
    }

    // Update Withdrawal
    public Withdrawal updateWithdrawal(Long id, Withdrawal updatedWithdrawal) {
        return withdrawalRepository.findById(id).map(existingWithdrawal -> {
            existingWithdrawal.setOrderId(updatedWithdrawal.getOrderId());
            existingWithdrawal.setTransactionType(updatedWithdrawal.getTransactionType());
            existingWithdrawal.setChannel(updatedWithdrawal.getChannel());
            existingWithdrawal.setStatus(updatedWithdrawal.getStatus());
            existingWithdrawal.setAmount(updatedWithdrawal.getAmount());
            existingWithdrawal.setFee(updatedWithdrawal.getFee());
            existingWithdrawal.setDateTime(updatedWithdrawal.getDateTime());
            return withdrawalRepository.save(existingWithdrawal);
        }).orElseThrow(() -> new RuntimeException("Withdrawal not found with ID: " + id));
    }

    // Menghapus Withdrawal
    public void deleteWithdrawal(Long id) {
        withdrawalRepository.deleteById(id);
    }
}