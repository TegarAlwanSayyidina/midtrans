package com.project.midtrans2.signature.service;

import com.project.midtrans2.signature.model.Signature;
import com.project.midtrans2.signature.repository.SignatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class SignatureService {

    private final SignatureRepository signatureRepository;
    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public SignatureService(SignatureRepository signatureRepository) {
        this.signatureRepository = signatureRepository;
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory.", e);
        }
    }

    public List<Signature> getAllSignatures() {
        return signatureRepository.findAll();
    }

    public Optional<Signature> getSignatureById(Long id) {
        return signatureRepository.findById(id);
    }

    public Signature saveSignature(Signature signature, MultipartFile file) throws IOException {
        if (file != null) {
            validateFile(file);
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            signature.setSignature(targetLocation.toString());
        }
        return signatureRepository.save(signature);
    }

    public void deleteSignature(Long id) {
        signatureRepository.deleteById(id);
    }

    private void validateFile(MultipartFile file) {
        if (file.getSize() > 1024 * 1024) {
            throw new IllegalArgumentException("File size exceeds 1 MB.");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type.");
        }
    }
}
