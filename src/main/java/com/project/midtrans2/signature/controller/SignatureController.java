package com.project.midtrans2.signature.controller;

import com.project.midtrans2.signature.model.Signature;
import com.project.midtrans2.signature.service.SignatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/signatures")
public class SignatureController {

    @Autowired
    private SignatureService signatureService;

    @GetMapping
    public List<Signature> getAllSignatures() {
        return signatureService.getAllSignatures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Signature> getSignatureById(@PathVariable Long id) {
        Signature signature = signatureService.getSignatureById(id);
        if (signature != null) {
            return ResponseEntity.ok(signature);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Signature> createSignature(
            @RequestParam String signatoryName,
            @RequestParam String signatoryRole,
            @RequestParam(required = false) MultipartFile signatureImage
    ) {
        try {
            Signature signature = signatureService.saveSignature(signatoryName, signatoryRole, signatureImage);
            return ResponseEntity.ok(signature);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Signature> updateSignature(
            @PathVariable Long id,
            @RequestParam String signatoryName,
            @RequestParam String signatoryRole,
            @RequestParam(required = false) MultipartFile signatureImage
    ) {
        try {
            Signature signature = signatureService.updateSignature(id, signatoryName, signatoryRole, signatureImage);
            if (signature != null) {
                return ResponseEntity.ok(signature);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        signatureService.deleteSignature(id);
        return ResponseEntity.noContent().build();
    }
}