package com.project.midtrans2.transactionlist.service;

import com.project.midtrans2.transactionlist.model.GeneralInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    public byte[] exportToExcel(List<GeneralInfo> generalInfoList) throws IOException {
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

        // Menulis workbook ke output stream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // Mengembalikan byte array dari file Excel
        return out.toByteArray();
    }
}
