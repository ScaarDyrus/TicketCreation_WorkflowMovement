package com.tagging.system.dto;

import com.tagging.system.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TicketDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String statusDisplayName;
    private String assignee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TagDTO> tags;

    public TicketDTO() {
    }

    public static TicketDTO fromEntity(Ticket ticket) {
        List<TagDTO> tagDTOs = ticket.getTags().stream()
                .map(TagDTO::fromEntity)
                .collect(Collectors.toList());
        
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setTitle(ticket.getTitle());
        dto.setDescription(ticket.getDescription());
        dto.setStatus(ticket.getStatus().name());
        dto.setStatusDisplayName(ticket.getStatus().getDisplayName());
        dto.setAssignee(ticket.getAssignee());
        dto.setCreatedAt(ticket.getCreatedAt());
        dto.setUpdatedAt(ticket.getUpdatedAt());
        dto.setTags(tagDTOs);
        
        return dto;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }
}
