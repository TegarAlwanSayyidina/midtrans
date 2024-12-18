package com.project.midtrans2.activitylog.repository;

import com.project.midtrans2.activitylog.model.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByEmail(String email);
    List<ActivityLog> findByUsername(String username);
    List<ActivityLog> findByIpAddress(String ipAddress);
    List<ActivityLog> findByAction(String action);
}
