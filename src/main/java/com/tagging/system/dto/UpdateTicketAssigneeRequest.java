package com.tagging.system.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateTicketAssigneeRequest {
    
    @NotBlank(message = "Assignee is required")
    private String assignee;
    
    private String userId;
    
    private String userName;
    
    private String comment;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
