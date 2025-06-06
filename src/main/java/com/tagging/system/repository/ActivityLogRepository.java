package com.tagging.system.repository;

import com.tagging.system.entity.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    Page<ActivityLog> findByTicketIdOrderByCreatedAtDesc(Long ticketId, Pageable pageable);
}
