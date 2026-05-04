package com.example.activitylog.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_activity_logs")
public class ActivityLog {

    @PrimaryKey
    private ActivityLogKey key;

    @Column("activity_type")
    private String activityType;

    public ActivityLog() {
    }

    public ActivityLog(ActivityLogKey key, String activityType) {
        this.key = key;
        this.activityType = activityType;
    }

    public ActivityLogKey getKey() {
        return key;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setKey(ActivityLogKey key) {
        this.key = key;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}