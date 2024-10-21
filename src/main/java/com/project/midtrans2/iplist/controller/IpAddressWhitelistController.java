package com.project.midtrans2.iplist.controller;

import com.project.midtrans2.iplist.model.IpAddressWhitelist;
import com.project.midtrans2.iplist.service.IpAddressWhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ip-whitelist")
public class IpAddressWhitelistController {

    @Autowired
    private IpAddressWhitelistService ipAddressWhitelistService;

    @GetMapping
    public List<IpAddressWhitelist> getAllIpAddresses() {
        return ipAddressWhitelistService.getAllIpAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IpAddressWhitelist> getIpAddressById(@PathVariable Long id) {
        Optional<IpAddressWhitelist> ipAddress = ipAddressWhitelistService.getIpAddressById(id);
        return ipAddress.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public IpAddressWhitelist createIpAddress(@RequestBody IpAddressWhitelist ipAddressWhitelist) {
        return ipAddressWhitelistService.createIpAddress(ipAddressWhitelist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IpAddressWhitelist> updateIpAddress(@PathVariable Long id, @RequestBody IpAddressWhitelist ipAddressWhitelist) {
        IpAddressWhitelist updatedIp = ipAddressWhitelistService.updateIpAddress(id, ipAddressWhitelist);
        if (updatedIp != null) {
            return ResponseEntity.ok(updatedIp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIpAddress(@PathVariable Long id) {
        ipAddressWhitelistService.deleteIpAddress(id);
        return ResponseEntity.noContent().build();
    }
}
