package com.tagging.system.dto;

import com.tagging.system.entity.Ticket;
import jakarta.validation.constraints.NotNull;

public class UpdateTicketStatusRequest {
    
    @NotNull(message = "Status is required")
    private Ticket.Status status;
    
    private String userId;
    
    private String userName;
    
    private String comment;

    public Ticket.Status getStatus() {
        return status;
    }

    public void setStatus(Ticket.Status status) {
        this.status = status;
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
