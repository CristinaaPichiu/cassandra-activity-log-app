package com.example.activitylog.controller;

import com.example.activitylog.dto.ActivityLogResponse;
import com.example.activitylog.dto.CreateActivityRequest;
import com.example.activitylog.service.ActivityLogService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/activities")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityLogResponse createActivity(
            @Valid @RequestBody CreateActivityRequest request
    ) {
        return activityLogService.createActivity(request);
    }

    @GetMapping("/user/{userId}")
    public List<ActivityLogResponse> getAllActivitiesForUser(
            @PathVariable String userId
    ) {
        return activityLogService.getAllActivitiesForUser(userId);
    }

    @GetMapping("/user/{userId}/recent")
    public List<ActivityLogResponse> getRecentActivitiesForUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return activityLogService.getRecentActivitiesForUser(userId, limit);
    }

    @GetMapping("/user/{userId}/range")
    public List<ActivityLogResponse> getActivitiesForUserWithinRange(
            @PathVariable String userId,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to
    ) {
        return activityLogService.getActivitiesForUserWithinRange(userId, from, to);
    }
}