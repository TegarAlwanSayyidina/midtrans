    package com.project.midtrans2.transactionlist.model;

    import java.math.BigDecimal;

    public class PromoDetails {
        private String promoId;
        private String promoName;
        private String promoCode;
        private String sponsorName;
        private String userIP;
        private BigDecimal transactionOriginalAmount;
        private BigDecimal discountAmount;
        private String voucherCode;

        // Getters dan Setters

        public BigDecimal getDiscountAmount() {

            return discountAmount;
        }

        public void setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getPromoCode() {
            return promoCode;
        }

        public void setPromoCode(String promoCode) {
            this.promoCode = promoCode;
        }

        public String getPromoId() {
            return promoId;
        }

        public void setPromoId(String promoId) {
            this.promoId = promoId;
        }

        public String getPromoName() {
            return promoName;
        }

        public void setPromoName(String promoName) {
            this.promoName = promoName;
        }

        public String getSponsorName() {
            return sponsorName;
        }

        public void setSponsorName(String sponsorName) {
            this.sponsorName = sponsorName;
        }

        public BigDecimal getTransactionOriginalAmount() {
            return transactionOriginalAmount;
        }

        public void setTransactionOriginalAmount(BigDecimal transactionOriginalAmount) {
            this.transactionOriginalAmount = transactionOriginalAmount;
        }

        public String getUserIP() {
            return userIP;
        }

        public void setUserIP(String userIP) {
            this.userIP = userIP;
        }

        public String getVoucherCode() {
            return voucherCode;
        }

        public void setVoucherCode(String voucherCode) {
            this.voucherCode = voucherCode;
        }
    }

