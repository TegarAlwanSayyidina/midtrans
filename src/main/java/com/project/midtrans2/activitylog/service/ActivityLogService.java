package com.project.midtrans2.activitylog.service;

import com.project.midtrans2.activitylog.model.ActivityLog;
import com.project.midtrans2.activitylog.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public List<ActivityLog> getAllActivityLogs() {
        return activityLogRepository.findAll();
    }

    public Optional<ActivityLog> getActivityLogById(Long id) {
        return activityLogRepository.findById(id);
    }

    public ActivityLog createActivityLog(ActivityLog activityLog) {
        return activityLogRepository.save(activityLog);
    }

    public ActivityLog updateActivityLog(Long id, ActivityLog newActivityLog) {
        return activityLogRepository.findById(id)
                .map(activityLog -> {
                    activityLog.setDate(newActivityLog.getDate());
                    activityLog.setUsername(newActivityLog.getUsername());
                    activityLog.setIpAddress(newActivityLog.getIpAddress());
                    activityLog.setAction(newActivityLog.getAction());
                    activityLog.setEmail(newActivityLog.getEmail());
                    activityLog.setTime(newActivityLog.getTime());
                    return activityLogRepository.save(activityLog);
                })
                .orElseThrow(() -> new RuntimeException("ActivityLog not found with id " + id));
    }

    public void deleteActivityLog(Long id) {
        activityLogRepository.deleteById(id);
    }

    // Metode pencarian dengan pengecekan jika hasil kosong
    public List<ActivityLog> getActivityLogsByEmail(String email) {
        List<ActivityLog> result = activityLogRepository.findByEmail(email);
        if (result.isEmpty()) {
            throw new NoSuchElementException("No activity logs found with email: " + email);
        }
        return result;
    }

    public List<ActivityLog> getActivityLogsByUsername(String username) {
        List<ActivityLog> result = activityLogRepository.findByUsername(username);
        if (result.isEmpty()) {
            throw new NoSuchElementException("No activity logs found with username: " + username);
        }
        return result;
    }

    public List<ActivityLog> getActivityLogsByIpAddress(String ipAddress) {
        List<ActivityLog> result = activityLogRepository.findByIpAddress(ipAddress);
        if (result.isEmpty()) {
            throw new NoSuchElementException("No activity logs found with IP address: " + ipAddress);
        }
        return result;
    }

    public List<ActivityLog> getActivityLogsByAction(String action) {
        List<ActivityLog> result = activityLogRepository.findByAction(action);
        if (result.isEmpty()) {
            throw new NoSuchElementException("No activity logs found with action: " + action);
        }
        return result;
    }
}
