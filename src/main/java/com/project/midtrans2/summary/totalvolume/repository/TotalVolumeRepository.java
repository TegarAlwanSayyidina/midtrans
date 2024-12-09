package com.project.midtrans2.summary.totalvolume.repository;

import com.project.midtrans2.summary.totalvolume.model.TotalVolume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalVolumeRepository extends JpaRepository<TotalVolume, Long> {
}
