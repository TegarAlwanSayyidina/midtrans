package com.project.midtrans2.transactionlist.controller;
import com.project.midtrans2.transactionlist.model.GeneralInfo;
import com.project.midtrans2.transactionlist.service.ExportService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/export")
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @PostMapping("/csv")
    public String exportToCSV(@RequestBody List<GeneralInfo> generalInfoList) {
        try {
            String filePath = "exported_data.csv"; // Tentukan path file
            exportService.exportToCSV(generalInfoList, filePath);
            return "Data berhasil diekspor ke CSV";
        } catch (IOException e) {
            return "Gagal mengekspor data: " + e.getMessage();
        }
    }

    @PostMapping("/xls")
    public String exportToExcel(@RequestBody List<GeneralInfo> generalInfoList) {
        try {
            String filePath = "exported_data.xlsx"; // Tentukan path file
            exportService.exportToExcel(generalInfoList, filePath);
            return "Data berhasil diekspor ke XLS";
        } catch (IOException e) {
            return "Gagal mengekspor data: " + e.getMessage();
        }
    }
}
  