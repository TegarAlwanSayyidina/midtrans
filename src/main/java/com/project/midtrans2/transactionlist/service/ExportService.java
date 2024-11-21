package com.project.midtrans2.transactionlist.service;

import com.project.midtrans2.transactionlist.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExportService {

    public void exportToCSV(List<GeneralInfo> generalInfoList, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
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
        }
    }

    public void exportToExcel(List<GeneralInfo> generalInfoList, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("General Info");

        // Header
        Row headerRow = sheet.createRow(0);
        String[] header = {"Date & Time", "Order ID", "Transaction ID", "Transaction Status", "Channel",
                "Receiver's Account Number", "Transaction Type", "Amount"};
        for (int i = 0; i < header.length; i++) {
            headerRow.createCell(i).setCellValue(header[i]);
        }

        // Data
        int rowNum = 1;
        for (GeneralInfo info : generalInfoList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(info.getDateTime().toString());
            row.createCell(1).setCellValue(info.getOrderId());
            row.createCell(2).setCellValue(info.getTransactionId());
            row.createCell(3).setCellValue(info.getTransactionStatus());
            row.createCell(4).setCellValue(info.getChannel());
            row.createCell(5).setCellValue(info.getReceiverAccountNumber());
            row.createCell(6).setCellValue(info.getTransactionType());
            row.createCell(7).setCellValue(info.getAmount().toString());
        }

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}