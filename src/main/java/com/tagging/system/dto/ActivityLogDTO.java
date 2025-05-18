package com.tagging.system.dto;

import com.tagging.system.entity.ActivityLog;
import java.time.LocalDateTime;

public class ActivityLogDTO {
    private Long id;
    private Long ticketId;
    private String userId;
    private String userName;
    private String activityType;
    private String oldValue;
    private String newValue;
    private String comment;
    private LocalDateTime createdAt;

    public static ActivityLogDTO fromEntity(ActivityLog activityLog) {
        ActivityLogDTO dto = new ActivityLogDTO();
        dto.setId(activityLog.getId());
        dto.setTicketId(activityLog.getTicket().getId());
        dto.setUserId(activityLog.getUserId());
        dto.setUserName(activityLog.getUserName());
        dto.setActivityType(activityLog.getActivityType().getDisplayName());
        dto.setOldValue(activityLog.getOldValue());
        dto.setNewValue(activityLog.getNewValue());
        dto.setComment(activityLog.getComment());
        dto.setCreatedAt(activityLog.getCreatedAt());
        return dto;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
