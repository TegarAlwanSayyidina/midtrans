package com.project.midtrans2.signature.service;

import com.project.midtrans2.signature.model.Signature;
import com.project.midtrans2.signature.repository.SignatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SignatureService {

    @Autowired
    private SignatureRepository signatureRepository;

    public List<Signature> getAllSignatures() {
        return signatureRepository.findAll();
    }

    public Signature getSignatureById(Long id) {
        return signatureRepository.findById(id).orElse(null);
    }

    public Signature saveSignature(String signatoryName, String signatoryRole, MultipartFile signatureImage) throws IOException {
        Signature signature = new Signature();
        signature.setSignatoryName(signatoryName);
        signature.setSignatoryRole(signatoryRole);
        if (signatureImage != null && !signatureImage.isEmpty()) {
            signature.setSignatureImage(signatureImage.getBytes());
        }
        return signatureRepository.save(signature);
    }

    public Signature updateSignature(Long id, String signatoryName, String signatoryRole, MultipartFile signatureImage) throws IOException {
        Signature signature = signatureRepository.findById(id).orElse(null);
        if (signature != null) {
            signature.setSignatoryName(signatoryName);
            signature.setSignatoryRole(signatoryRole);
            if (signatureImage != null && !signatureImage.isEmpty()) {
                signature.setSignatureImage(signatureImage.getBytes());
            }
            return signatureRepository.save(signature);
        }
        return null;
    }

    public void deleteSignature(Long id) {
        signatureRepository.deleteById(id);
    }
}