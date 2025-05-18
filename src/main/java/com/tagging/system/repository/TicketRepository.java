package com.tagging.system.repository;

import com.tagging.system.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.tags tag WHERE tag.id = :tagId")
    Page<Ticket> findByTagId(@Param("tagId") Long tagId, Pageable pageable);
    
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.tags tag WHERE tag.name = :tagName")
    Page<Ticket> findByTagName(@Param("tagName") String tagName, Pageable pageable);
    
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.tags tag WHERE tag.id IN :tagIds")
    Page<Ticket> findByTagIds(@Param("tagIds") List<Long> tagIds, Pageable pageable);
    
    @Query("SELECT DISTINCT t FROM Ticket t JOIN t.tags tag WHERE tag.name IN :tagNames")
    Page<Ticket> findByTagNames(@Param("tagNames") List<String> tagNames, Pageable pageable);
    
    @Query(value = "SELECT t.* FROM tickets t " +
           "JOIN ticket_tags tt ON t.id = tt.ticket_id " +
           "JOIN tags tag ON tt.tag_id = tag.id " +
           "WHERE tag.name IN :tagNames " +
           "GROUP BY t.id " +
           "HAVING COUNT(DISTINCT tag.id) = :tagCount", nativeQuery = true)
    Page<Ticket> findByAllTagNames(@Param("tagNames") List<String> tagNames, @Param("tagCount") Long tagCount, Pageable pageable);
}
