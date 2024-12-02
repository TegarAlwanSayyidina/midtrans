package com.project.midtrans2.invoicetemplate.controller;

import com.project.midtrans2.invoicetemplate.model.InvoiceTemplate;
import com.project.midtrans2.invoicetemplate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // Save a new invoice
    @PostMapping("/save")
    public ResponseEntity<InvoiceTemplate> saveInvoice(@RequestBody InvoiceTemplate template) {
        InvoiceTemplate savedTemplate = invoiceService.saveInvoice(template);
        return ResponseEntity.ok(savedTemplate);
    }

    // Generate text template for an invoice
    @PostMapping("/generate")
    public ResponseEntity<String> generateInvoice(@RequestBody InvoiceTemplate template) {
        String invoiceText = invoiceService.generateInvoiceText(template);
        return ResponseEntity.ok(invoiceText);
    }
}
