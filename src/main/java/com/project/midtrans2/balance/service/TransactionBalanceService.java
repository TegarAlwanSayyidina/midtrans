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

    private final TransactionBalanceRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionBalanceService.class);

    public TransactionBalanceService(TransactionBalanceRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionBalance> filterTransactions(TransactionFilterRequest filterRequest, LocalDate startDate, LocalDate endDate) {
        logger.info("Filtering transactions with request: {}, startDate: {}, endDate: {}", filterRequest, startDate, endDate);

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate cannot be null");
        }

        List<TransactionBalance> transactions = transactionRepository.filterTransactions(
                filterRequest.getPaymentType(),
                filterRequest.getStatus(),
                filterRequest.getPaymentChannel(),
                startDate,
                endDate
        );
        logger.info("Filtered transactions count: {}", transactions.size());
        return transactions;
    }

    private String getTransactionStatusFromGateway(String orderId) {
        logger.info("Fetching transaction status from payment gateway for orderId: {}", orderId);
        return "Success"; // Placeholder, customize as needed
    }

    public String handleTransactionStatus(String orderId) {
        logger.info("Updating transaction status for orderId: {}", orderId);
        String status = getTransactionStatusFromGateway(orderId);

        if (orderId == null || orderId.trim().isEmpty()) {
            logger.warn("Received invalid orderId: {}", orderId);
            return "Invalid orderId provided";
        }

        Optional<TransactionBalance> transactionOptional = transactionRepository.findByOrderId(orderId);
        if (transactionOptional.isPresent()) {
            TransactionBalance transaction = transactionOptional.get();
            transaction.setStatus(status);
            transactionRepository.save(transaction);
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

        Optional<TransactionBalance> transaction = transactionRepository.findByOrderId(orderId);
        if (transaction.isPresent()) {
            transaction.get().setStatus(status);
            transactionRepository.save(transaction.get());
            return "Transaction status updated to: " + status;
        }

        return "Transaction not found for orderId: " + orderId;
    }

    private boolean validateNotificationSignature(String signatureKey, String orderId, Integer grossAmount) {
        // Implement signature validation logic here
        return true;
    }

    public List<TransactionBalance> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<TransactionBalance> getTransactionByOrderId(String orderId) {
        return transactionRepository.findByOrderId(orderId);
    }

    public String refundTransaction(String orderId, double amount) {
        if (orderId == null || orderId.trim().isEmpty()) {
            logger.warn("Invalid orderId for refund: {}", orderId);
            return "Invalid orderId provided";
        }

        Optional<TransactionBalance> transaction = transactionRepository.findByOrderId(orderId);
        if (transaction.isPresent()) {
            // Implement actual refund logic here
            logger.info("Refund successful for orderId: {}", orderId);
            return "Refund successful for orderId: " + orderId;
        } else {
            logger.warn("Transaction not found for refund request with orderId: {}", orderId);
            return "Transaction not found for orderId: " + orderId;
        }
    }

    public TransactionBalance saveTransaction(TransactionBalance transaction) {
        return transactionRepository.save(transaction);
    }
}