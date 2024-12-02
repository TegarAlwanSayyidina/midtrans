package com.project.midtrans2.invoicetemplate.service;


import com.project.midtrans2.invoicetemplate.model.InvoiceTemplate;
import com.project.midtrans2.invoicetemplate.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceTemplate saveInvoice(InvoiceTemplate invoiceTemplate) {
        return invoiceRepository.save(invoiceTemplate);
    }

    public String generateInvoiceText(InvoiceTemplate template) {
        return "Halo " + template.getCustomerName() + ",\n\n" +
                (template.getHeader() != null ? template.getHeader() + "\n\n" : "") +
                "No. Invoice: " + template.getInvoiceNumber() + "\n" +
                "Tanggal Invoice: " + template.getInvoiceDate() + "\n" +
                "Total Tagihan: " + template.getTotalAmount() + "\n" +
                "Jatuh Tempo: " + template.getDueDate() + "\n\n" +
                "Klik link ini untuk download invoice:\n" +
                template.getDownloadLink() + "\n\n" +
                "Bayar tagihan invoice via:\n" +
                template.getPaymentInfo() + "\n\n" +
                "Regards,\n\n" +
                "Salon Mamah";
    }
}

