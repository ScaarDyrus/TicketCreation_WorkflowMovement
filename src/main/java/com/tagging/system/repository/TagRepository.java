package com.tagging.system.repository;

import com.tagging.system.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Tag> findByNameContainingIgnoreCase(@Param("query") String query);
    
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT(:query, '%'))")
    List<Tag> findByNameStartingWithIgnoreCase(@Param("query") String query);
    
    @Query("SELECT t FROM Tag t ORDER BY t.usageCount DESC")
    Page<Tag> findMostUsedTags(Pageable pageable);
    
    @Query(value = "SELECT t.* FROM tags t JOIN ticket_tags tt ON t.id = tt.tag_id WHERE tt.ticket_id = :ticketId", nativeQuery = true)
    List<Tag> findTagsByTicketId(@Param("ticketId") Long ticketId);
}
