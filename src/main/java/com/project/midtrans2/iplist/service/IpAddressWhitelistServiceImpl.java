package com.project.midtrans2.iplist.service;

import com.project.midtrans2.iplist.model.IpAddressWhitelist;
import com.project.midtrans2.iplist.repository.IpAddressWhitelistRepository;
import com.project.midtrans2.iplist.model.IpAddressWhitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IpAddressWhitelistServiceImpl implements IpAddressWhitelistService {

    @Autowired
    private IpAddressWhitelistRepository ipAddressWhitelistRepository;

    @Override
    public List<IpAddressWhitelist> getAllIpAddresses() {
        return ipAddressWhitelistRepository.findAll();
    }

    @Override
    public Optional<IpAddressWhitelist> getIpAddressById(Long id) {
        return ipAddressWhitelistRepository.findById(id);
    }

    @Override
    public IpAddressWhitelist createIpAddress(IpAddressWhitelist ipAddressWhitelist) {
        return ipAddressWhitelistRepository.save(ipAddressWhitelist);
    }

    @Override
    public IpAddressWhitelist updateIpAddress(Long id, IpAddressWhitelist ipAddressWhitelist) {
        Optional<IpAddressWhitelist> existingIp = ipAddressWhitelistRepository.findById(id);
        if (existingIp.isPresent()) {
            IpAddressWhitelist updatedIp = existingIp.get();
            updatedIp.setIpAddress(ipAddressWhitelist.getIpAddress());
            updatedIp.setDescription(ipAddressWhitelist.getDescription());
            return ipAddressWhitelistRepository.save(updatedIp);
        } else {
            return null;
        }
    }

    @Override
    public void deleteIpAddress(Long id) {
        ipAddressWhitelistRepository.deleteById(id);
    }
}
