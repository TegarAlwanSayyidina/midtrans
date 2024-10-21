package com.project.midtrans2.transactionlist.controller;

import com.project.midtrans2.transactionlist.model.GeneralInfo;
import com.project.midtrans2.transactionlist.service.ExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.opencsv.CSVWriter;  // Import CSVWriter
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping("/csv")
    public ResponseEntity<?> exportToCSV(@RequestBody List<GeneralInfo> generalInfoList) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream));

            String[] header = {"Date & Time", "Order ID", "Transaction ID", "Transaction Status", "Channel",
                    "Receiver's Account Number", "Transaction Type", "Amount"};
            writer.writeNext(header);

            for (GeneralInfo info : generalInfoList) {
                String[] data = {
                        info.getDateTime().toString(),
                        info.getOrderId(),
                        info.getTransactionId(),
                        info.getTransactionStatus(),
                        info.getChannel(),
                        info.getReceiverAccountNumber(),
                        info.getTransactionType(),
                        info.getAmount().toString()
                };
                writer.writeNext(data);
            }
            writer.close();

            String filename = "exported_data.csv";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputStream.toByteArray());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Gagal mengekspor data: " + e.getMessage());
        }
    }

    @PostMapping("/xls")
    public ResponseEntity<byte[]> exportToExcel(@RequestBody List<GeneralInfo> generalInfoList) {
        try {
            byte[] excelData = exportService.exportToExcel(generalInfoList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "exported_data.xlsx");

            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
