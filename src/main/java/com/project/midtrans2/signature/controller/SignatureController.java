package com.project.midtrans2.signature.controller;

import com.project.midtrans2.signature.model.Signature;
import com.project.midtrans2.signature.service.SignatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/signatures")
public class SignatureController {

    private final SignatureService signatureService;

    public SignatureController(SignatureService signatureService) {
        this.signatureService = signatureService;
    }

    @GetMapping
    public List<Signature> getAllSignatures() {
        return signatureService.getAllSignatures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Signature> getSignatureById(@PathVariable Long id) {
        return signatureService.getSignatureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createSignature(
            @RequestPart("signature") Signature signature,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !isValidImage(file)) {
                return ResponseEntity.badRequest().body("Invalid file type. Only JPEG or PNG allowed.");
            }

            Signature savedSignature = signatureService.saveSignature(signature, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSignature);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save signature.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSignature(
            @PathVariable Long id,
            @RequestPart("signature") Signature updatedSignature,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            return signatureService.getSignatureById(id).map(existingSignature -> {
                try {
                    if (file != null && !isValidImage(file)) {
                        return ResponseEntity.badRequest().body("Invalid file type. Only JPEG or PNG allowed.");
                    }

                    updatedSignature.setId(id); // Set ID to the existing entity
                    Signature savedSignature = signatureService.saveSignature(updatedSignature, file);
                    return ResponseEntity.ok(savedSignature);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update signature.");
                }
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSignature(@PathVariable Long id) {
        if (signatureService.getSignatureById(id).isPresent()) {
            signatureService.deleteSignature(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Utility method to validate uploaded file.
     */
    private boolean isValidImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png"));
    }
}
