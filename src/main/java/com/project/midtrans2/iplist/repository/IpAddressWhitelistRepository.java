package com.project.midtrans2.iplist.repository;

import com.project.midtrans2.iplist.model.IpAddressWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IpAddressWhitelistRepository extends JpaRepository<IpAddressWhitelist, Long> {
    // Tambahkan query kustom jika diperlukan
}