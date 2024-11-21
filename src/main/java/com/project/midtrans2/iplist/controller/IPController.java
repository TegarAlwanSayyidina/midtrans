package com.project.midtrans2.iplist.controller;

import com.project.midtrans2.iplist.model.IP;
import com.project.midtrans2.iplist.service.IPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ip")
@CrossOrigin(origins = "http://localhost:4200")
public class IPController {

    @Autowired
    private IPService ipService;

    @GetMapping
    public ResponseEntity<List<IP>> getAllIPs() {
        List<IP> ips = ipService.findAll();
        return new ResponseEntity<>(ips, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IP> getIPById(@PathVariable Long id) {
        return ipService.findById(id)
                .map(ip -> new ResponseEntity<>(ip, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<IP> createIP(@RequestBody IP ip) {
        IP savedIP = ipService.save(ip);
        return new ResponseEntity<>(savedIP, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IP> updateIP(@PathVariable Long id, @RequestBody IP ipDetails) {
        return ipService.findById(id)
                .map(ip -> {
                    ip.setIpAddress(ipDetails.getIpAddress());
                    IP updatedIP = ipService.save(ip);
                    return new ResponseEntity<>(updatedIP, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIP(@PathVariable Long id) {
        if (ipService.findById(id).isPresent()) {
            ipService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
