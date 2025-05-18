package com.tagging.system.service;

import com.tagging.system.dto.*;
import com.tagging.system.entity.Tag;
import com.tagging.system.entity.Ticket;
import com.tagging.system.repository.TagRepository;
import com.tagging.system.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TagRepository tagRepository;
    private final ActivityLogService activityLogService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TagRepository tagRepository, ActivityLogService activityLogService) {
        this.ticketRepository = ticketRepository;
        this.tagRepository = tagRepository;
        this.activityLogService = activityLogService;
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable)
                .map(TicketDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));
        return TicketDTO.fromEntity(ticket);
    }

    @Transactional
    public TicketDTO createTicket(CreateTicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(Ticket.Status.BACKLOG);

        // Add tags if provided
        if (request.getTagNames() != null && !request.getTagNames().isEmpty()) {
            for (String tagName : request.getTagNames()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return tagRepository.save(newTag);
                        });
                ticket.addTag(tag);
            }
        }

        Ticket savedTicket = ticketRepository.save(ticket);
        
        // Log ticket creation
        String userId = "system";
        String userName = "System";
        activityLogService.logTicketCreated(savedTicket, userId, userName);
        
        return TicketDTO.fromEntity(savedTicket);
    }

    @Transactional
    public TicketDTO updateTicket(Long id, CreateTicketRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        String oldTitle = ticket.getTitle();
        String oldDescription = ticket.getDescription();
        
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());

        Ticket updatedTicket = ticketRepository.save(ticket);
        
        // Log changes
        String userId = "system";
        String userName = "System";
        
        if (!oldTitle.equals(request.getTitle())) {
            activityLogService.logTitleChanged(updatedTicket, oldTitle, request.getTitle(), userId, userName);
        }
        
        if ((oldDescription == null && request.getDescription() != null) || 
            (oldDescription != null && !oldDescription.equals(request.getDescription()))) {
            activityLogService.logDescriptionChanged(updatedTicket, oldDescription, request.getDescription(), userId, userName);
        }
        
        return TicketDTO.fromEntity(updatedTicket);
    }

    @Transactional
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        // Remove all tags from this ticket
        for (Tag tag : new ArrayList<>(ticket.getTags())) {
            ticket.removeTag(tag);
        }

        ticketRepository.delete(ticket);
    }

    @Transactional
    public TicketDTO addTagsToTicket(Long ticketId, AddTagsToTicketRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + ticketId));

        String userId = request.getUserId() != null ? request.getUserId() : "system";
        String userName = request.getUserName() != null ? request.getUserName() : "System";
        
        for (String tagName : request.getTagNames()) {
            Tag tag = tagRepository.findByName(tagName)
                    .orElseGet(() -> {
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        return tagRepository.save(newTag);
                    });
            
            if (!ticket.getTags().contains(tag)) {
                ticket.addTag(tag);
                activityLogService.logTagAdded(ticket, tag, userId, userName);
            }
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return TicketDTO.fromEntity(updatedTicket);
    }

    @Transactional
    public TicketDTO removeTagFromTicket(Long ticketId, Long tagId, String userId, String userName) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + ticketId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + tagId));

        if (ticket.getTags().contains(tag)) {
            ticket.removeTag(tag);
            
            // Default values if not provided
            userId = userId != null ? userId : "system";
            userName = userName != null ? userName : "System";
            
            activityLogService.logTagRemoved(ticket, tag, userId, userName);
        }
        
        Ticket updatedTicket = ticketRepository.save(ticket);
        return TicketDTO.fromEntity(updatedTicket);
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> findTicketsByTagId(Long tagId, Pageable pageable) {
        return ticketRepository.findByTagId(tagId, pageable)
                .map(TicketDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> findTicketsByTagName(String tagName, Pageable pageable) {
        return ticketRepository.findByTagName(tagName, pageable)
                .map(TicketDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> findTicketsByTagIds(List<Long> tagIds, Pageable pageable) {
        return ticketRepository.findByTagIds(tagIds, pageable)
                .map(TicketDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> findTicketsByTagNames(List<String> tagNames, Pageable pageable) {
        return ticketRepository.findByTagNames(tagNames, pageable)
                .map(TicketDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<TicketDTO> findTicketsByAllTagNames(List<String> tagNames, Pageable pageable) {
        return ticketRepository.findByAllTagNames(tagNames, (long) tagNames.size(), pageable)
                .map(TicketDTO::fromEntity);
    }

    @Transactional
    public TicketDTO updateTicketStatus(Long id, UpdateTicketStatusRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        Ticket.Status oldStatus = ticket.getStatus();
        ticket.setStatus(request.getStatus());
        
        Ticket updatedTicket = ticketRepository.save(ticket);
        
        // Default values if not provided
        String userId = request.getUserId() != null ? request.getUserId() : "system";
        String userName = request.getUserName() != null ? request.getUserName() : "System";
        
        activityLogService.logStatusChanged(updatedTicket, oldStatus, request.getStatus(), 
                                           userId, userName, request.getComment());
        
        return TicketDTO.fromEntity(updatedTicket);
    }
    
    @Transactional
    public TicketDTO updateTicketAssignee(Long id, UpdateTicketAssigneeRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));

        String oldAssignee = ticket.getAssignee();
        ticket.setAssignee(request.getAssignee());
        
        Ticket updatedTicket = ticketRepository.save(ticket);
        
        // Default values if not provided
        String userId = request.getUserId() != null ? request.getUserId() : "system";
        String userName = request.getUserName() != null ? request.getUserName() : "System";
        
        activityLogService.logAssigneeChanged(updatedTicket, oldAssignee, request.getAssignee(), 
                                             userId, userName, request.getComment());
        
        return TicketDTO.fromEntity(updatedTicket);
    }
    
    @Transactional
    public TicketDTO addComment(Long id, String comment, String userId, String userName) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));
        
        // Default values if not provided
        userId = userId != null ? userId : "system";
        userName = userName != null ? userName : "System";
        
        activityLogService.logCommentAdded(ticket, comment, userId, userName);
        
        return TicketDTO.fromEntity(ticket);
    }
}
