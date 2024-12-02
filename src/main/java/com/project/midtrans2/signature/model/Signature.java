package com.project.midtrans2.signature.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Signature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String signatoryName;

    @Column(length = 20, nullable = false)
    private String signatoryRole;

    @Lob
    private byte[] signatureImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSignatoryName() {
        return signatoryName;
    }

    public void setSignatoryName(String signatoryName) {
        this.signatoryName = signatoryName;
    }

    public String getSignatoryRole() {
        return signatoryRole;
    }

    public void setSignatoryRole(String signatoryRole) {
        this.signatoryRole = signatoryRole;
    }

    public byte[] getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(byte[] signatureImage) {
        this.signatureImage = signatureImage;
    }
}
