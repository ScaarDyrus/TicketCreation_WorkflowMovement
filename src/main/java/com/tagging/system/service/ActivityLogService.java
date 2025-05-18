package com.tagging.system.service;

import com.tagging.system.dto.ActivityLogDTO;
import com.tagging.system.entity.ActivityLog;
import com.tagging.system.entity.Tag;
import com.tagging.system.entity.Ticket;
import com.tagging.system.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityLogService {

    private final ActivityLogRepository activityLogRepository;

    @Autowired
    public ActivityLogService(ActivityLogRepository activityLogRepository) {
        this.activityLogRepository = activityLogRepository;
    }

    @Transactional(readOnly = true)
    public Page<ActivityLogDTO> getActivityLogsByTicketId(Long ticketId, Pageable pageable) {
        return activityLogRepository.findByTicketIdOrderByCreatedAtDesc(ticketId, pageable)
                .map(ActivityLogDTO::fromEntity);
    }

    @Transactional
    public void logTicketCreated(Ticket ticket, String userId, String userName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.TICKET_CREATED);
        activityLog.setNewValue(ticket.getTitle());
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logStatusChanged(Ticket ticket, Ticket.Status oldStatus, Ticket.Status newStatus, 
                                String userId, String userName, String comment) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.STATUS_CHANGED);
        activityLog.setOldValue(oldStatus != null ? oldStatus.getDisplayName() : null);
        activityLog.setNewValue(newStatus.getDisplayName());
        activityLog.setComment(comment);
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logAssigneeChanged(Ticket ticket, String oldAssignee, String newAssignee, 
                                  String userId, String userName, String comment) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.ASSIGNEE_CHANGED);
        activityLog.setOldValue(oldAssignee);
        activityLog.setNewValue(newAssignee);
        activityLog.setComment(comment);
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logTitleChanged(Ticket ticket, String oldTitle, String newTitle, 
                               String userId, String userName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.TITLE_CHANGED);
        activityLog.setOldValue(oldTitle);
        activityLog.setNewValue(newTitle);
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logDescriptionChanged(Ticket ticket, String oldDescription, String newDescription, 
                                     String userId, String userName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.DESCRIPTION_CHANGED);
        activityLog.setOldValue(oldDescription);
        activityLog.setNewValue(newDescription);
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logTagAdded(Ticket ticket, Tag tag, String userId, String userName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.TAG_ADDED);
        activityLog.setNewValue(tag.getName());
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logTagRemoved(Ticket ticket, Tag tag, String userId, String userName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.TAG_REMOVED);
        activityLog.setOldValue(tag.getName());
        
        activityLogRepository.save(activityLog);
    }

    @Transactional
    public void logCommentAdded(Ticket ticket, String comment, String userId, String userName) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setTicket(ticket);
        activityLog.setUserId(userId);
        activityLog.setUserName(userName);
        activityLog.setActivityType(ActivityLog.ActivityType.COMMENT_ADDED);
        activityLog.setNewValue(comment);
        
        activityLogRepository.save(activityLog);
    }
}
