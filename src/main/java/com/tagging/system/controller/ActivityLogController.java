package com.tagging.system.controller;

import com.tagging.system.dto.ActivityLogDTO;
import com.tagging.system.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @Autowired
    public ActivityLogController(ActivityLogService activityLogService) {
        this.activityLogService = activityLogService;
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<Page<ActivityLogDTO>> getActivityLogsByTicketId(
            @PathVariable Long ticketId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(activityLogService.getActivityLogsByTicketId(ticketId, pageable));
    }
}
