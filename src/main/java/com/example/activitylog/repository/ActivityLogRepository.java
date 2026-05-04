package com.example.activitylog.repository;

import com.example.activitylog.model.ActivityLog;
import com.example.activitylog.model.ActivityLogKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ActivityLogRepository extends CassandraRepository<ActivityLog, ActivityLogKey> {

    @Query("SELECT * FROM user_activity_logs WHERE user_id = ?0")
    List<ActivityLog> findAllByUserId(String userId);

    @Query("SELECT * FROM user_activity_logs WHERE user_id = ?0 LIMIT ?1")
    List<ActivityLog> findRecentByUserId(String userId, int limit);

    @Query("""
           SELECT * FROM user_activity_logs
           WHERE user_id = ?0
           AND activity_timestamp >= ?1
           AND activity_timestamp <= ?2
           """)
    List<ActivityLog> findByUserIdAndTimestampBetween(String userId, Instant from, Instant to);
}