package com.project.midtrans2.summary.totalvolume.service;

import com.project.midtrans2.summary.totalvolume.model.TotalVolume;
import com.project.midtrans2.summary.totalvolume.repository.TotalVolumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TotalVolumeService {

    @Autowired
    private TotalVolumeRepository totalVolumeRepository;

    public TotalVolume saveTotalVolume(TotalVolume totalVolume) {
        return totalVolumeRepository.save(totalVolume);
    }

    public BigDecimal getTotalVolumeMonthToDate() {
        List<TotalVolume> volumes = totalVolumeRepository.findAll();
        return volumes.stream()
                .filter(v -> v.getTransactionDate().getMonth().equals(LocalDate.now().getMonth()))
                .map(TotalVolume::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
