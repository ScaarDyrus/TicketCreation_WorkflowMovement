package com.tagging.system.controller;

import com.tagging.system.dto.*;
import com.tagging.system.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> getAllTickets(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ticketService.getAllTickets(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@Valid @RequestBody CreateTicketRequest request) {
        TicketDTO createdTicket = ticketService.createTicket(request);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Long id, @Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<TicketDTO> addTagsToTicket(@PathVariable Long id, @Valid @RequestBody AddTagsToTicketRequest request) {
        return ResponseEntity.ok(ticketService.addTagsToTicket(id, request));
    }

    @DeleteMapping("/{ticketId}/tags/{tagId}")
    public ResponseEntity<TicketDTO> removeTagFromTicket(
            @PathVariable Long ticketId, 
            @PathVariable Long tagId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userName) {
        return ResponseEntity.ok(ticketService.removeTagFromTicket(ticketId, tagId, userId, userName));
    }

    @GetMapping("/by-tag/{tagId}")
    public ResponseEntity<Page<TicketDTO>> getTicketsByTagId(@PathVariable Long tagId, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ticketService.findTicketsByTagId(tagId, pageable));
    }

    @GetMapping("/by-tag-name/{tagName}")
    public ResponseEntity<Page<TicketDTO>> getTicketsByTagName(@PathVariable String tagName, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ticketService.findTicketsByTagName(tagName, pageable));
    }

    @GetMapping("/by-tag-ids")
    public ResponseEntity<Page<TicketDTO>> getTicketsByTagIds(@RequestParam List<Long> tagIds, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ticketService.findTicketsByTagIds(tagIds, pageable));
    }

    @GetMapping("/by-tag-names")
    public ResponseEntity<Page<TicketDTO>> getTicketsByTagNames(@RequestParam List<String> tagNames, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ticketService.findTicketsByTagNames(tagNames, pageable));
    }

    @GetMapping("/by-all-tag-names")
    public ResponseEntity<Page<TicketDTO>> getTicketsByAllTagNames(@RequestParam List<String> tagNames, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(ticketService.findTicketsByAllTagNames(tagNames, pageable));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateTicketStatus(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateTicketStatusRequest request) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, request));
    }
    
    @PatchMapping("/{id}/assignee")
    public ResponseEntity<TicketDTO> updateTicketAssignee(
            @PathVariable Long id, 
            @Valid @RequestBody UpdateTicketAssigneeRequest request) {
        return ResponseEntity.ok(ticketService.updateTicketAssignee(id, request));
    }
    
    @PostMapping("/{id}/comments")
    public ResponseEntity<TicketDTO> addComment(
            @PathVariable Long id,
            @RequestParam String comment,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String userName) {
        return ResponseEntity.ok(ticketService.addComment(id, comment, userId, userName));
    }
}
