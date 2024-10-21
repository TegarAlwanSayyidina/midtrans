package com.project.midtrans2.iplist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ip_address_whitelist")
public class IpAddressWhitelist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", nullable = false, unique = true)
    private String ipAddress;

    @Column(name = "description")
    private String description;

    public IpAddressWhitelist() {}

    public IpAddressWhitelist(String ipAddress, String description) {
        this.ipAddress = ipAddress;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
