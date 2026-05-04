package com.example.activitylog.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@PrimaryKeyClass
public class ActivityLogKey implements Serializable {

    @PrimaryKeyColumn(name = "user_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String userId;

    @PrimaryKeyColumn(
            name = "activity_timestamp",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 1,
            ordering = Ordering.DESCENDING
    )
    private Instant activityTimestamp;

    @PrimaryKeyColumn(name = "activity_id", type = PrimaryKeyType.CLUSTERED, ordinal = 2)
    private UUID activityId;

    public ActivityLogKey() {
    }

    public ActivityLogKey(String userId, Instant activityTimestamp, UUID activityId) {
        this.userId = userId;
        this.activityTimestamp = activityTimestamp;
        this.activityId = activityId;
    }

    public String getUserId() {
        return userId;
    }

    public Instant getActivityTimestamp() {
        return activityTimestamp;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setActivityTimestamp(Instant activityTimestamp) {
        this.activityTimestamp = activityTimestamp;
    }

    public void setActivityId(UUID activityId) {
        this.activityId = activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityLogKey that)) return false;
        return Objects.equals(userId, that.userId)
                && Objects.equals(activityTimestamp, that.activityTimestamp)
                && Objects.equals(activityId, that.activityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, activityTimestamp, activityId);
    }
}