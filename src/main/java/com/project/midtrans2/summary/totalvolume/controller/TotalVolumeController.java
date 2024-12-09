package com.project.midtrans2.summary.totalvolume.controller;

import com.project.midtrans2.summary.totalvolume.model.TotalVolume;
import com.project.midtrans2.summary.totalvolume.model.TotalVolumeResponse;
import com.project.midtrans2.summary.totalvolume.service.TotalVolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/total-volume")
@CrossOrigin("http://localhost:4200")
public class TotalVolumeController {

    @Autowired
    private TotalVolumeService totalVolumeService;

    @PostMapping("/add")
    public ResponseEntity<TotalVolume> addTotalVolume(@RequestBody TotalVolume totalVolume) {
        TotalVolume savedVolume = totalVolumeService.saveTotalVolume(totalVolume);
        return ResponseEntity.ok(savedVolume);
    }

    @GetMapping("/month-to-date")
    public ResponseEntity<TotalVolumeResponse> getTotalVolumeMonthToDate() {
        BigDecimal totalVolume = totalVolumeService.getTotalVolumeMonthToDate();
        return ResponseEntity.ok(new TotalVolumeResponse(totalVolume));
    }
}
