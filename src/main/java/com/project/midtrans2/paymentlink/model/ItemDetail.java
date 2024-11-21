package com.project.midtrans2.paymentlink.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // Import JsonIgnore
import jakarta.persistence.*;

@Entity
public class ItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;
    private Integer quantity;
    private String brand;
    private String category;
    private String merchantName;

    @ManyToOne
    @JoinColumn(name = "payment_link_id")
    @JsonIgnore // Tambahkan @JsonIgnore di sini untuk menghindari masalah nesting
    private TransactionLink paymentLink;

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public TransactionLink getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(TransactionLink paymentLink) {
        this.paymentLink = paymentLink;
    }
}