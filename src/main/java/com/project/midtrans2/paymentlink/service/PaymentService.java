package com.project.midtrans2.paymentlink.service;


import com.project.midtrans2.paymentlink.model.DynamicAmount;
import com.project.midtrans2.paymentlink.model.ItemDetail;
import com.project.midtrans2.paymentlink.model.TransactionLink;
import com.project.midtrans2.paymentlink.repository.TransactionPaymentLinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.project.midtrans2.paymentlink.exception.PaymentLinkNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PaymentService {

    private final TransactionPaymentLinkRepository paymentLinkRepository;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(TransactionPaymentLinkRepository paymentLinkRepository) {
        this.paymentLinkRepository = paymentLinkRepository;
    }

    public Map<String, Object> createPaymentLink(Map<String, Object> request) {
        TransactionLink paymentLink = new TransactionLink();
        Map<String, Object> transactionDetails = (Map<String, Object>) request.get("transaction_details");
        Map<String, Object> customerDetails = (Map<String, Object>) request.get("customer_details");
        Map<String, Object> expiryDetails = (Map<String, Object>) request.get("expiry");
        Map<String, Object> dynamicAmount = (Map<String, Object>) request.get("dynamic_amount");

        // Ambil metode pembayaran terpilih dan simpan ke paymentLink
        String selectedPaymentMethod = (String) request.get("selected_payment_method");
        paymentLink.setSelectedPaymentMethod(selectedPaymentMethod);

        // Assign data dari request ke entity PaymentLink
        paymentLink.setOrderId((String) transactionDetails.get("order_id"));
        paymentLink.setGrossAmount((Integer) transactionDetails.get("gross_amount"));
        paymentLink.setCustomerRequired((Boolean) request.get("customer_required"));
        paymentLink.setFirstName((String) customerDetails.get("first_name"));
        paymentLink.setLastName((String) customerDetails.get("last_name"));
        paymentLink.setEmail((String) customerDetails.get("email"));
        paymentLink.setPhone((String) customerDetails.get("phone"));
        paymentLink.setNotes((String) customerDetails.get("notes"));
        paymentLink.setTitle((String) request.get("title"));
        paymentLink.setCustomField1((String) request.get("custom_field1"));
        paymentLink.setCustomField2((String) request.get("custom_field2"));
        paymentLink.setCustomField3((String) request.get("custom_field3"));
        paymentLink.setCallbackUrl((String) ((Map<String, Object>) request.get("callbacks")).get("finish"));

        // Set expiry details
        String startTimeStr = ((String) expiryDetails.get("start_time")).replaceAll("\\s+", ""); // Hilangkan semua spasi ekstra
        Integer duration = (Integer) expiryDetails.get("duration");
        String unit = (String) expiryDetails.get("unit");

        paymentLink.setStartTime(startTimeStr);
        paymentLink.setDuration(duration);
        paymentLink.setUnit(unit);
        paymentLink.setUsageLimit((Integer) request.get("usage_limit"));

        // Validasi waktu expiry: Konversi start_time dan cek apakah sudah lewat
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"); // Format sesuai dengan input
        LocalDateTime startTime;

        try {
            startTime = LocalDateTime.parse(startTimeStr, formatter);
        } catch (Exception e) {
            logger.error("Error parsing start_time: {}", e.getMessage());
            throw new RuntimeException("Format waktu tidak valid: " + startTimeStr);
        }

        LocalDateTime expiryTime = unit.equalsIgnoreCase("minutes") ? startTime.plusMinutes(duration) : startTime;
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("Asia/Jakarta")); // Sesuaikan zona waktu

        if (currentTime.isAfter(expiryTime)) {
            throw new RuntimeException("Gagal membuat PaymentLink: Waktu sudah kadaluwarsa.");
        }

        // Set dynamic amount details
        if (dynamicAmount != null) {
            DynamicAmount dynamicAmountEntity = new DynamicAmount();
            dynamicAmountEntity.setMinAmount((Integer) dynamicAmount.get("min_amount"));
            dynamicAmountEntity.setMaxAmount((Integer) dynamicAmount.get("max_amount"));
            dynamicAmountEntity.setPresetAmount((Integer) dynamicAmount.get("preset_amount"));
            paymentLink.setDynamicAmount(dynamicAmountEntity);
        }

        // Map item_details dari request ke PaymentLink
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("item_details");
        List<ItemDetail> itemDetails = new ArrayList<>();

        for (Map<String, Object> item : items) {
            ItemDetail itemDetail = new ItemDetail();
            itemDetail.setName((String) item.get("name"));
            itemDetail.setPrice((Integer) item.get("price"));
            itemDetail.setQuantity((Integer) item.get("quantity"));
            itemDetail.setBrand((String) item.get("brand"));
            itemDetail.setCategory((String) item.get("category"));
            itemDetail.setMerchantName((String) item.get("merchant_name"));
            // Hapus referensi ke paymentLink untuk menghindari sirkular
            // itemDetail.setPaymentLink(paymentLink);
            itemDetails.add(itemDetail);
        }

        paymentLink.setItemDetails(itemDetails);

        // Address handling
        String address = customerDetails.get("address") != null ? (String) customerDetails.get("address") : "";
        String city = customerDetails.get("city") != null ? (String) customerDetails.get("city") : "";
        String postalCode = customerDetails.get("postal_code") != null ? (String) customerDetails.get("postal_code") : "";
        String country = customerDetails.get("country") != null ? (String) customerDetails.get("country") : "";

        String fullAddress = String.join(", ", address, city, postalCode, country);
        paymentLink.setCustomerAddress(fullAddress);

        try {
            // Simpan ke database
            paymentLinkRepository.save(paymentLink);
            logger.info("PaymentLink {} berhasil disimpan ke database.", paymentLink.getOrderId());

            // Buat URL PaymentLink lokal
            String paymentLinkUrl = "https://example.com/payment/" + paymentLink.getOrderId();
            logger.info("PaymentLink berhasil dibuat dengan URL: {}", paymentLinkUrl);

            // Kembalikan response sebagai Map
            return Map.of(
                    "message", "Payment link created successfully!",
                    "payment_link_url", paymentLinkUrl
            );
        } catch (Exception e) {
            logger.error("Terjadi kesalahan saat menyimpan PaymentLink: {}", e.getMessage());
            throw new RuntimeException("Gagal membuat PaymentLink", e);
        }
    }

    public TransactionLink getPaymentLinkByOrderId(String orderId) {
        Optional<TransactionLink> paymentLinkOptional = paymentLinkRepository.findByOrderId(orderId);
        if (paymentLinkOptional.isPresent()) {
            return paymentLinkOptional.get();
        } else {
            logger.warn("PaymentLink tidak ditemukan untuk orderId: {}", orderId);
            throw new PaymentLinkNotFoundException("PaymentLink tidak ditemukan untuk orderId: " + orderId);
        }
    }
}