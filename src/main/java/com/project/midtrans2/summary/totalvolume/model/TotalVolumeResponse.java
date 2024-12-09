package com.project.midtrans2.summary.totalvolume.model;

import java.math.BigDecimal;

public class TotalVolumeResponse {

    private BigDecimal amount;

    public TotalVolumeResponse(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
