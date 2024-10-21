package com.project.midtrans2.iplist.service;

import com.project.midtrans2.iplist.model.IpAddressWhitelist;

import java.util.List;
import java.util.Optional;

public interface IpAddressWhitelistService {
    List<IpAddressWhitelist> getAllIpAddresses();
    Optional<IpAddressWhitelist> getIpAddressById(Long id);
    IpAddressWhitelist createIpAddress(IpAddressWhitelist ipAddressWhitelist);
    IpAddressWhitelist updateIpAddress(Long id, IpAddressWhitelist ipAddressWhitelist);
    void deleteIpAddress(Long id);
}
