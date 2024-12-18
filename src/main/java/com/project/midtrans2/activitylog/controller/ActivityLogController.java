package com.project.midtrans2.activitylog.controller;

import com.project.midtrans2.activitylog.model.ActivityLog;
import com.project.midtrans2.activitylog.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/activitylogs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public List<ActivityLog> getAllActivityLogs() {
        return activityLogService.getAllActivityLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityLog> getActivityLogById(@PathVariable Long id) {
        return activityLogService.getActivityLogById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ActivityLog createActivityLog(@RequestBody ActivityLog activityLog) {
        return activityLogService.createActivityLog(activityLog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityLog> updateActivityLog(@PathVariable Long id, @RequestBody ActivityLog activityLog) {
        return ResponseEntity.ok(activityLogService.updateActivityLog(id, activityLog));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivityLog(@PathVariable Long id) {
        activityLogService.deleteActivityLog(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pencarian
    @GetMapping("/search/email")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(activityLogService.getActivityLogsByEmail(email));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/username")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(activityLogService.getActivityLogsByUsername(username));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/ipaddress")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByIpAddress(@RequestParam String ipAddress) {
        try {
            return ResponseEntity.ok(activityLogService.getActivityLogsByIpAddress(ipAddress));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/action")
    public ResponseEntity<List<ActivityLog>> getActivityLogsByAction(@RequestParam String action) {
        try {
            return ResponseEntity.ok(activityLogService.getActivityLogsByAction(action));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
