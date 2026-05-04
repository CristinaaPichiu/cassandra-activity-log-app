package com.example.activitylog.service;

import com.example.activitylog.dto.ActivityLogResponse;
import com.example.activitylog.dto.CreateActivityRequest;
import com.example.activitylog.model.ActivityLog;
import com.example.activitylog.model.ActivityLogKey;
import com.example.activitylog.repository.ActivityLogRepository;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityLogService {

    private static final Duration ACTIVITY_LOG_TTL = Duration.ofDays(30);

    private final ActivityLogRepository activityLogRepository;
    private final CassandraTemplate cassandraTemplate;

    public ActivityLogService(
            ActivityLogRepository activityLogRepository,
            CassandraTemplate cassandraTemplate
    ) {
        this.activityLogRepository = activityLogRepository;
        this.cassandraTemplate = cassandraTemplate;
    }

    public ActivityLogResponse createActivity(CreateActivityRequest request) {
        ActivityLogKey key = new ActivityLogKey(
                request.userId(),
                Instant.now(),
                UUID.randomUUID()
        );

        ActivityLog activityLog = new ActivityLog(
                key,
                request.activityType()
        );

        cassandraTemplate.insert(
                activityLog,
                InsertOptions.builder()
                        .ttl(ACTIVITY_LOG_TTL)
                        .build()
        );

        return toResponse(activityLog);
    }

    public List<ActivityLogResponse> getAllActivitiesForUser(String userId) {
        return activityLogRepository.findAllByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ActivityLogResponse> getRecentActivitiesForUser(String userId, int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be greater than 0");
        }

        return activityLogRepository.findRecentByUserId(userId, limit)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ActivityLogResponse> getActivitiesForUserWithinRange(
            String userId,
            Instant from,
            Instant to
    ) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must be before to");
        }

        return activityLogRepository.findByUserIdAndTimestampBetween(userId, from, to)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ActivityLogResponse toResponse(ActivityLog activityLog) {
        ActivityLogKey key = activityLog.getKey();

        return new ActivityLogResponse(
                key.getUserId(),
                key.getActivityId(),
                activityLog.getActivityType(),
                key.getActivityTimestamp()
        );
    }
}