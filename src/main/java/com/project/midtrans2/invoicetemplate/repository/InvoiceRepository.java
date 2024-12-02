package com.project.midtrans2.invoicetemplate.repository;


import com.project.midtrans2.invoicetemplate.model.InvoiceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceTemplate, Long> {
}

