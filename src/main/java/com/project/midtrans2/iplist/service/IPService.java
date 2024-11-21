package com.project.midtrans2.iplist.service;

import com.project.midtrans2.iplist.model.IP;
import com.project.midtrans2.iplist.repository.IPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IPService {

    @Autowired
    private IPRepository ipRepository;

    public List<IP> findAll() {
        return ipRepository.findAll();
    }

    public Optional<IP> findById(Long id) {
        return ipRepository.findById(id);
    }

    public IP save(IP ip) {
        Optional<IP> existingIP = ipRepository.findByIpAddress(ip.getIpAddress());
        if (existingIP.isPresent()) {
            throw new IllegalArgumentException("IP address already exists: " + ip.getIpAddress());
        }
        return ipRepository.save(ip);
    }

    public void deleteById(Long id) {
        ipRepository.deleteById(id);
    }
}
