package com.project.midtrans2.transactionlist.controller;

import com.project.midtrans2.transactionlist.model.GeneralInfo;
import com.project.midtrans2.transactionlist.service.ExportService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    // Endpoint untuk export CSV
    @PostMapping("/csv")
    public String exportToCSV(@RequestBody GeneralInfo generalInfo) {
        try {
            // Bungkus single object menjadi List
            List<GeneralInfo> generalInfoList = Collections.singletonList(generalInfo);
            String filePath = "exported_data.csv"; // Path file
            exportService.exportToCSV(generalInfoList, filePath);
            return "Data berhasil diekspor ke CSV di " + filePath;
        } catch (IOException e) {
            return "Gagal mengekspor data: " + e.getMessage();
        }
    }

    // Endpoint untuk export Excel
    @PostMapping("/xls")
    public String exportToExcel(@RequestBody GeneralInfo generalInfo) {
        try {
            // Bungkus single object menjadi List
            List<GeneralInfo> generalInfoList = Collections.singletonList(generalInfo);
            String filePath = "exported_data.xlsx"; // Path file
            exportService.exportToExcel(generalInfoList, filePath);
            return "Data berhasil diekspor ke XLS di " + filePath;
        } catch (IOException e) {
            return "Gagal mengekspor data: " + e.getMessage();
        }
    }
}
