package com.example.activitylog.dto;

import java.time.Instant;
import java.util.UUID;

public record ActivityLogResponse(
        String userId,
        UUID activityId,
        String activityType,
        Instant timestamp
) {
}