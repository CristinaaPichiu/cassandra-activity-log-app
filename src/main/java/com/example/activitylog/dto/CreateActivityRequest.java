package com.example.activitylog.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateActivityRequest(
        @NotBlank(message = "userId is required")
        String userId,

        @NotBlank(message = "activityType is required")
        String activityType
) {
}