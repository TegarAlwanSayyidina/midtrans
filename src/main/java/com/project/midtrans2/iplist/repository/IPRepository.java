package com.project.midtrans2.iplist.repository;

import com.project.midtrans2.iplist.model.IP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPRepository extends JpaRepository<IP, Long> {
    Optional<IP> findByIpAddress(String ipAddress);
}
