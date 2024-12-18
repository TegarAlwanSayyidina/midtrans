package com.project.midtrans2.balance.service;

import com.project.midtrans2.balance.model.TransactionBalance;
import com.project.midtrans2.balance.dto.TransactionFilterRequest;
import com.project.midtrans2.balance.repository.TransactionBalanceRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionBalanceService {

    private final TransactionBalanceRepository transactionBalanceRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionBalanceService.class);

    public TransactionBalanceService(TransactionBalanceRepository transactionBalanceRepository) {
        this.transactionBalanceRepository = transactionBalanceRepository;
    }

    public List<TransactionBalance> filterTransactions(TransactionFilterRequest filterRequest, LocalDate startDate, LocalDate endDate) {
        logger.info("Filtering transactions with request: {}, startDate: {}, endDate: {}", filterRequest, startDate, endDate);

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate cannot be null");
        }

        List<TransactionBalance> transactions = transactionBalanceRepository.filterTransactions(
                filterRequest.getPaymentType(),
                filterRequest.getStatus(),
                filterRequest.getPaymentChannel(),
                startDate,
                endDate
        );
        logger.info("Filtered transactions count: {}", transactions.size());
        return transactions;
    }

    public String handleTransactionStatus(String orderId) {
        logger.info("Updating transaction status for orderId: {}", orderId);

        if (orderId == null || orderId.trim().isEmpty()) {
            logger.warn("Received invalid orderId: {}", orderId);
            return "Invalid orderId provided";
        }

        Optional<TransactionBalance> transactionOptional = transactionBalanceRepository.findByOrderId(orderId);
        if (transactionOptional.isPresent()) {
            TransactionBalance transaction = transactionOptional.get();
            String status = getTransactionStatusFromGateway(orderId);
            transaction.setStatus(status);
            transactionBalanceRepository.save(transaction);
            logger.info("Transaction status updated to: {}", status);
            return "Transaction status updated to: " + status;
        } else {
            logger.warn("Transaction not found for orderId: {}", orderId);
            return "Transaction not found for orderId: " + orderId;
        }
    }

    public String handlePaymentNotification(Map<String, Object> notification) {
        String orderId = (String) notification.get("order_id");
        String status = (String) notification.get("transaction_status");
        Integer grossAmount = Integer.parseInt((String) notification.get("gross_amount"));
        String signatureKey = (String) notification.get("signature_key");

        if (!validateNotificationSignature(signatureKey, orderId, grossAmount)) {
            logger.error("Invalid signature key for orderId: {}", orderId);
            return "Invalid signature";
        }

        if (orderId == null || status == null) {
            logger.warn("Invalid notification payload: missing orderId or status.");
            return "Invalid notification payload";
        }

        Optional<TransactionBalance> transaction = transactionBalanceRepository.findByOrderId(orderId);
        if (transaction.isPresent()) {
            TransactionBalance existingTransaction = transaction.get();
            existingTransaction.setStatus(status);
            transactionBalanceRepository.save(existingTransaction);
            return "Transaction status updated to: " + status;
        }

        return "Transaction not found for orderId: " + orderId;
    }

    private boolean validateNotificationSignature(String signatureKey, String orderId, Integer grossAmount) {
        // Implement signature validation logic here
        return true;
    }

    public List<TransactionBalance> getAllTransactions() {
        return transactionBalanceRepository.findAll();
    }

    public Optional<TransactionBalance> getTransactionByOrderId(String orderId) {
        return transactionBalanceRepository.findByOrderId(orderId);
    }

    public String refundTransaction(String orderId, double amount) {
        if (orderId == null || orderId.trim().isEmpty()) {
            logger.warn("Invalid orderId for refund: {}", orderId);
            return "Invalid orderId provided";
        }

        Optional<TransactionBalance> transaction = transactionBalanceRepository.findByOrderId(orderId);
        if (transaction.isPresent()) {
            // Refund logic (e.g., reverse the transaction)
            logger.info("Refund successful for orderId: {}", orderId);
            return "Refund successful for orderId: " + orderId;
        } else {
            logger.warn("Transaction not found for refund request with orderId: {}", orderId);
            return "Transaction not found for orderId: " + orderId;
        }
    }

    public TransactionBalance saveTransaction(TransactionBalance transaction) {
        TransactionBalance savedTransaction = transactionBalanceRepository.save(transaction);

        // Update withdrawable balance if the transaction is a successful withdrawal
        if ("Withdrawal".equalsIgnoreCase(transaction.getPaymentType()) &&
                "Success".equalsIgnoreCase(transaction.getStatus())) {
            updateWithdrawableBalance(transaction.getOrderId(), transaction.getGrossAmount());
        }

        return savedTransaction;
    }

    private void updateWithdrawableBalance(String orderId, double amount) {
        Optional<TransactionBalance> optionalBalance = transactionBalanceRepository.findByOrderId(orderId);

        if (optionalBalance.isPresent()) {
            TransactionBalance balance = optionalBalance.get();
            double newBalance = balance.getGrossAmount() + amount;  // Add the gross amount to the balance
            balance.setGrossAmount((int) newBalance);  // Set the new balance
            transactionBalanceRepository.save(balance);

            logger.info("Updated withdrawable balance for orderId {}: New Balance: {}", orderId, newBalance);
        } else {
            logger.warn("Transaction not found for updating balance, orderId: {}", orderId);
        }
    }

    private String getTransactionStatusFromGateway(String orderId) {
        logger.info("Fetching transaction status from payment gateway for orderId: {}", orderId);
        return "Success"; // Placeholder, customize as needed
    }

    public Double getWithdrawableBalance() {
        return transactionBalanceRepository.calculateWithdrawableBalance();
    }

    public Double getWithdrawableBalanceWithinDates(LocalDate startDate, LocalDate endDate) {
        return transactionBalanceRepository.calculateWithdrawableBalanceWithinDates(startDate, endDate);
    }
}
